package nl.sander.bejava;

import nl.sander.bejava.api.*;
import nl.sander.bejava.classinfo.FieldInfo;
import nl.sander.bejava.classinfo.MethodInfo;
import nl.sander.bejava.constantpool.entry.Utf8Entry;

/**
 * Transforms BeeSource to CompiledClass
 * A client must supply a {@link BeSource} containing a set of {@link CodeLine}s that is assumed to be correct.
 * It doesn't check if a valid state is reached.
 */
public class Compiler {
    private final CompiledClass compiledClass;
    private final ConstantPoolCreator constantPoolCreator = new ConstantPoolCreator();
    private Utf8Entry codeAttributeNameEntry;

    /**
     * construct a compiler object.
     */
    /*
     * At this moment I'm not sure if this class will be able to reused.
     */
    public Compiler(CompiledClass compiledClass) {
        this.compiledClass = compiledClass;
    }

    /**
     * compile a BeeSource object into a CompiledClass object.
     *
     * @param beSource the Class object for which the constant pool needs to be created
     * @return a CompiledClass object (that can be turned into bytecode)
     */
    public static CompiledClass compile(BeSource beSource) {
        return new Compiler(new CompiledClass(beSource)).compile();
    }

    /**
     * construct a CompiledClass object that contains all information for generating the bytecode
     */
    public CompiledClass compile() {
        compiledClass.getSource().getFields().forEach(this::addToConstantPool);
        compiledClass.getSource().getConstructors().forEach(this::addToConstantPool);
        compiledClass.getSource().getMethods().forEach(this::addToConstantPool); // compiledClass.getSource() ?

        addClassAndSuperClassEntriesToCompiledClass();

        var constantPool = constantPoolCreator.createConstantPool(compiledClass.getConstantTree());

        compiledClass.setConstantPool(constantPool);

        // add refs to super interfaces to class descriptor and constantpool
        compiledClass.getSource().getInterfaces().forEach(interfase -> {
            var interfaceEntry = ConstantPoolEntryCreator.getOrCreateClassEntry(interfase);
            compiledClass.addInterface(interfaceEntry);
            compiledClass.addConstantPoolEntry(interfaceEntry);
        });

        // add refs to fields to class descriptor
        compiledClass.getSource().getFields().stream()
                .map(field ->
                        new FieldInfo(ConstantPoolEntryCreator.getOrCreateUtf8(field.getName()),
                                ConstantPoolEntryCreator.getOrCreateUtf8(internalName(field.getType().getName())))
                                .addAccessFlags(field.getAccessFlags())
                )
                .forEach(compiledClass::addField);

        addConstructors();
        addMethods();

        return compiledClass;
    }

    // why not put this everywhere, it's not like it's ever going to change
    private String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }



    private void addClassAndSuperClassEntriesToCompiledClass() {
        var classEntry = ConstantPoolEntryCreator.getOrCreateClassEntry(compiledClass.getSource().getName());
        compiledClass.setThisClass(classEntry);
        compiledClass.addConstantPoolEntry(classEntry);

        var superClass = ConstantPoolEntryCreator.getOrCreateClassEntry(compiledClass.getSource().getSuperClass());
        compiledClass.addConstantPoolEntry(superClass);
        compiledClass.setSuperClass(superClass);


        this.codeAttributeNameEntry = ConstantPoolEntryCreator.getOrCreateUtf8("Code");
        compiledClass.addConstantPoolEntry(codeAttributeNameEntry);
    }

    public void addConstructors() {
        compiledClass.getSource().getConstructors().stream()
                .map(this::createMethod)
                .forEach(compiledClass::addMethod);
    }

    /**
     * maps methods from the source to a MethodInfo and adds that to the CompiledClass.
     */
    public void addMethods() {
        compiledClass.addConstantPoolEntry(codeAttributeNameEntry);
        for (var method : compiledClass.getSource().getMethods()) {
            compiledClass.addMethod(createMethod(method));
        }
    }

    /**
     * create methodInfo object for classfile
     */
    private MethodInfo createMethod(CodeContainer method) {
        return new MethodInfo(
                ConstantPoolEntryCreator.getOrCreateUtf8(method.getName()),
                ConstantPoolEntryCreator.getOrCreateUtf8(method.getSignature()))
                .addAccessFlags(method.getAccessFlags())
                .addAttribute(MethodCodeAttributeCreator.createCodeAttribute(codeAttributeNameEntry, method));
    }

    private void addToConstantPool(BeField field) {
        var fieldRefEntry = ConstantPoolEntryCreator.getOrCreateFieldRefEntry(compiledClass.getSource().getName(), field.getName(), field.getType());
        compiledClass.addConstantPoolEntry(fieldRefEntry);
    }

    /**
     * inspect a method or constructor, extract items that need to be added, and add them to the constant pool
     */
    private void addToConstantPool(CodeContainer codeContainer) {
        compiledClass.addConstantPoolEntry(ConstantPoolEntryCreator.getOrCreateUtf8(codeContainer.getName()));
        compiledClass.addConstantPoolEntry(ConstantPoolEntryCreator.getOrCreateUtf8(codeContainer.getSignature()));
        codeContainer.getExpandedCode().forEach(this::updateConstantPool);
    }

    /**
     * Scan code line for items that need adding to the constant pool
     *
     * Constantpool uniqueness is maintained in two ways:
     * 1. ConstantPoolEntryCreator.getOrCreateMethodRefEntry (and other methods) return a tree of entries.
     *      Sub-entries are kept in cache by the ConstantPoolEntryCreator and it returns a cached entry over a new one, if deemed equal.
     * 2. the root node (also in aforementioned cache) is not symmetric because it is the only one that can be added to The compiledClass.
     *      So Compiled class also contains a set of unique root nodes.
     */
    private void updateConstantPool(JavaInstruction instruction) {
        if (instruction.hasMethodRef()) {
            compiledClass.addConstantPoolEntry(instruction.getMethodRef());
        }

        if (instruction.hasFieldRef()) {
            compiledClass.addConstantPoolEntry(instruction.getFieldRef());
        }

        if (instruction.hasConstantRef()) {
            compiledClass.addConstantPoolEntry(instruction.getConstantEntry());
        }
    }
}

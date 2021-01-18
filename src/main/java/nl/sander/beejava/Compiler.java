package nl.sander.beejava;

import nl.sander.beejava.api.*;
import nl.sander.beejava.classinfo.FieldInfo;
import nl.sander.beejava.classinfo.MethodInfo;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ClassEntry;
import nl.sander.beejava.constantpool.entry.FieldRefEntry;
import nl.sander.beejava.constantpool.entry.Utf8Entry;

/**
 * Builds a set of a tree of constant pool entries that refer to each other.
 * <p>
 * A client must supply a {@link BeeSource} containing a set of {@link CodeLine}s that is assumed to be correct.
 * It doesn't check if a valid state is reached.
 */
/* So the name isn't entirely correct. Waiting for inspiration.
 * also TODO make sure entries aren't duplicates
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
     * @param beeSource the Class object for which the constant pool needs to be created
     * @return a CompiledClass object (that can be turned into bytecode)
     */
    public static CompiledClass compile(BeeSource beeSource) {
        return new Compiler(new CompiledClass(beeSource)).compile();
    }

    /**
     * construct a CompiledClass object that contains all information for generating the bytecode
     */
    public CompiledClass compile() {
        compiledClass.getSource().getFields().forEach(this::addToConstantPool);
        compiledClass.getSource().getConstructors().forEach(this::addToConstantPool);
        compiledClass.getSource().getMethods().forEach(this::addToConstantPool); // compiledClass.getSource() ?

        addClassAndSuperClassEntriesToCompiledClass();

        ConstantPool constantPool = constantPoolCreator.createConstantPool(compiledClass.getConstantTree());

        compiledClass.setConstantPool(constantPool);

        // add refs to super interfaces to class descriptor and constantpool
        compiledClass.getSource().getInterfaces().forEach(interfase -> {
            ClassEntry interfaceEntry = ConstantPoolEntryCreator.getOrCreateClassEntry(interfase);
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
        ClassEntry classEntry = ConstantPoolEntryCreator.getOrCreateClassEntry(compiledClass.getSource().getName());
        compiledClass.setThisClass(classEntry);
        compiledClass.addConstantPoolEntry(classEntry);

        ClassEntry superClass = ConstantPoolEntryCreator.getOrCreateClassEntry(compiledClass.getSource().getSuperClass());
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
        for (BeeMethod method : compiledClass.getSource().getMethods()) {
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

    private void addToConstantPool(BeeField field) {
        FieldRefEntry fieldRefEntry = ConstantPoolEntryCreator.getOrCreateFieldRefEntry(compiledClass.getSource().getName(), field.getName(), field.getType());
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

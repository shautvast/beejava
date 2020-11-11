package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.api.CodeLine;

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
    private final ConstantPoolEntryCreator constantPoolEntryCreator;

    /**
     * construct a compiler object.
     */
    /*
     * At this moment I'm not sure if this class will be able to reused.
     */
    public Compiler(CompiledClass compiledClass) {
        this.compiledClass = compiledClass;
        this.constantPoolEntryCreator = new ConstantPoolEntryCreator(compiledClass);
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
        compiledClass.getSource().getConstructors().forEach(this::updateConstantPool);
        compiledClass.getSource().getMethods().forEach(this::updateConstantPool);
        // TODO update constant pool for fields ?

        compiledClass.setThisClass(constantPoolEntryCreator.addThisClass());
        constantPoolEntryCreator.addInterfaces();
        constantPoolEntryCreator.addFields();

        return compiledClass;
    }

    /*
     * inspect a method or constructor, extract items that need to be added, and add them to the constant pool
     */
    private void updateConstantPool(CodeContainer codeContainer) {
        codeContainer.getCode().forEach(this::updateConstantPool);
    }

    /*
     * Scan code line for items that need adding to the constant pool
     *
     * Constantpool uniqueness is maintained in two ways:
     * 1. ConstantPoolEntryCreator.getOrCreateMethodRefEntry (and other methods) return a tree of entries.
     *      Sub-entries are kept in cache by the ConstantPoolEntryCreator and it returns a cached entry over a new one, if deemed equal.
     * 2. the root node (also in aforementioned cache) is not symmetric because it is the only one that can be added to The compiledClass.
     *      So Compiled class also contains a set of unique root nodes.
     */
    private void updateConstantPool(CodeLine codeline) {
        if (codeline.hasMethodCall()) {
            compiledClass.addConstantPoolEntry(constantPoolEntryCreator.getOrCreateMethodRefEntry(codeline));
        }

        if (codeline.hasRefToOwnField() || codeline.hasRefToExternalField()) {
            compiledClass.addConstantPoolEntry(constantPoolEntryCreator.getOrCreateFieldRefEntry(codeline));
        }

        if (codeline.hasConstValue()) {
            compiledClass.addConstantPoolEntry(constantPoolEntryCreator.getOrCreatePrimitiveEntry(codeline));
        }
    }
}

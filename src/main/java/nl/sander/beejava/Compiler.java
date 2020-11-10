package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.api.CodeLine;

/**
 * Builds a set of a tree of constant pool entries that refer to each other.
 * <p>
 * A client must supply a {@link BeeClass} containing a set of {@link CodeLine}s that is assumed to be correct.
 * It doesn't check if a valid state is reached.
 */
/* So the name isn't entirely correct. Waiting for inspiration.
 * also TODO make sure entries aren't duplicates
 */
public class Compiler {
    private final CompiledClass compiledClass;
    private final ConstantPoolEntryCreator constantPoolEntryCreator;

    Compiler(CompiledClass compiledClass) {
        this.compiledClass = compiledClass;
        this.constantPoolEntryCreator = new ConstantPoolEntryCreator(compiledClass);
    }

    /**
     * Creates a Set of nested entries that make up a single reference. For instance a class reference whose name is a utf8 reference.
     * In the constant pool they are consecutive entries, but they are created like nodes in a tree structure that models their relation.
     *
     * @param beeClass the Class object for which the constant pool needs to be created
     * @return a Set of constant pool entries
     */
    public static CompiledClass compile(BeeClass beeClass) {
        return new Compiler(new CompiledClass(beeClass)).compile();
    }

    CompiledClass compile() {
        compiledClass.getBeeClass().getConstructors().forEach(this::updateConstantPool);
        compiledClass.getBeeClass().getMethods().forEach(this::updateConstantPool);
        // TODO update constant pool for fields ?
        // TODO update constant pool for methods

        constantPoolEntryCreator.addThisClass();
        constantPoolEntryCreator.addInterfaces();
        constantPoolEntryCreator.addFields();

        return compiledClass;
    }


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

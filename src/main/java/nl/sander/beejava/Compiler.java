package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.constantpool.entry.*;

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
    private CompiledClass compiledClass;

    /**
     * Creates a Set of nested entries that make up a single reference. For instance a class reference whose name is a utf8 reference.
     * In the constant pool they are consecutive entries, but they are created like nodes in a tree structure that models their relation.
     *
     * @param beeClass the Class object for which the constant pool needs to be created
     * @return a Set of constant pool entries
     */
    public CompiledClass compile(BeeClass beeClass) {
        compiledClass = new CompiledClass(beeClass);
        beeClass.getConstructors().forEach(this::updateConstantPool);
        // TODO update constantTree for fields ?
        // TODO update constantTree for methods

        compiledClass.setThisClass();
        compiledClass.setInterfaces();

        return compiledClass;
    }


    private void updateConstantPool(ContainsCode codeContainer) {
        codeContainer.getCode().forEach(this::updateConstantPool);
    }

    /*
     * scan code line for items that need adding to the constant pool
     */
    private void updateConstantPool(CodeLine codeline) {
        if (codeline.hasMethod()) {
            addMethod(codeline);
        }

        if (codeline.hasField()) {
            addField(codeline);
        }
    }

    private void addMethod(CodeLine codeline) {
        compiledClass.addConstantPoolEntry(new MethodRefEntry(getOrCreateClassEntry(codeline), createMethodNameAndType(codeline)));
    }

    private void addField(CodeLine codeline) {
        compiledClass.addConstantPoolEntry(new FieldRefEntry(getOrCreateClassEntry(codeline), createFieldNameAndType(codeline)));
    }

    private NameAndTypeEntry createMethodNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(new Utf8Entry(codeline.getMethodName()), new Utf8Entry(codeline.getMethodSignature()));
    }

    private NameAndTypeEntry createFieldNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(new Utf8Entry(codeline.getField().getName()), new Utf8Entry(TypeMapper.map(codeline.getField().getType())));
    }

    private ClassEntry getOrCreateClassEntry(CodeLine codeline) {
        if (codeline.getRef() == Ref.SUPER) {
            return compiledClass.superClass();
        } else if (codeline.getRef() == Ref.THIS) {
            return compiledClass.thisClass();
        }
        //TODO other cases
        throw new RuntimeException("shouldn't be here");
    }

}

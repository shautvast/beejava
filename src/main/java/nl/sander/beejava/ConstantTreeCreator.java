package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.constantpool.entry.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Builds a set of a tree of constant pool entries that refer to each other.
 *
 * A client must supply a {@link BeeClass} containing a set of {@link CodeLine}s that is assumed to be correct.
 * It doesn't check if a valid state is reached.
 */
/* So the name isn't entirely correct. Waiting for inspiration.
 * also TODO make sure entries aren't duplicates
 */
public class ConstantTreeCreator {
    private final Set<NodeConstant> constantTree = new LinkedHashSet<>();
    private BeeClass beeClass;

    /**
     * Creates a Set of nested entries that make up a single reference. For instance a class reference whose name is a utf8 reference.
     * In the constant pool they are consecutive entries, but they are created like nodes in a tree structure that models their relation.
     *
     * @param beeClass the Class object for which the constant pool needs to be created
     * @return a Set of constant pool entries
     */
    public Set<NodeConstant> createConstantTree(BeeClass beeClass) {
        constantTree.clear();
        this.beeClass = beeClass;
        beeClass.getConstructors().forEach(this::updateConstantTree);
        // TODO update constantTree for fields ?
        // TODO update constantTree for methods
        return constantTree;
    }

    private void updateConstantTree(ContainsCode codeContainer) {
        codeContainer.getCode().forEach(this::updateConstantTree);
    }

    /*
     * scan code line for items that need adding to the constant pool
     */
    private void updateConstantTree(CodeLine codeline) {
        if (codeline.hasMethod()){
            addMethod(codeline);
        }

        if (codeline.hasField()) {
            addField(codeline);
        }
    }

    private void addMethod(CodeLine codeline) {
        constantTree.add(new ConstantMethodRef(createClassName(codeline), createMethodNameAndType(codeline)));
    }

    private void addField(CodeLine codeline) {
        constantTree.add(new ConstantFieldRef(createClassName(codeline), createFieldNameAndType(codeline)));
    }

    private ConstantNameAndType createMethodNameAndType(CodeLine codeline) {
        return new ConstantNameAndType(new ConstantUtf8(codeline.getMethodName()), new ConstantUtf8(codeline.getMethodSignature()));
    }

    private ConstantNameAndType createFieldNameAndType(CodeLine codeline) {
        return new ConstantNameAndType(new ConstantUtf8(codeline.getField().getName()), new ConstantUtf8(TypeMapper.map(codeline.getField().getType())));
    }

    private ConstantClass createClassName(CodeLine codeline) {
        return new ConstantClass(new ConstantUtf8(internalName(getNameOfClass(codeline))));
    }

    private String getNameOfClass(CodeLine codeline) {
        if (codeline.getRef() == Ref.SUPER) {
            return beeClass.getSuperClass().getName();
        } else if (codeline.getRef() == Ref.THIS) {
            return beeClass.getName();
        }
        throw new RuntimeException("shouldn't be here");
    }

    private String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }

}

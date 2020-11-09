package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.constantpool.entry.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Builds a set of a tree of constant pool entries that refer to each other.
 * <p>
 * A client must supply a {@link BeeClass} containing a set of {@link CodeLine}s that is assumed to be correct.
 * It doesn't check if a valid state is reached.
 */
/* So the name isn't entirely correct. Waiting for inspiration.
 * also TODO make sure entries aren't duplicates
 */
public class ConstantTreeCreator {
    private final Set<NodeConstant> constantTree = new LinkedHashSet<>();
    private BeeClass beeClass;
    private ClassEntry thisClass;
    private ClassEntry superClass;


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

        constantTree.add(getThisClassRef(beeClass));
        return constantTree;
    }

    /*
     * might be null if no methods refer to _this_
     */
    private ClassEntry getThisClassRef(BeeClass beeClass) {
        if (thisClass == null) {
            thisClass = new ClassEntry(new Utf8Entry(internalName(beeClass.getName())));
        }
        return thisClass;
    }


    public ClassEntry getThisClass() {
        return thisClass;
    }

    public ClassEntry getSuperClass() {
        return superClass;
    }

    private void updateConstantTree(ContainsCode codeContainer) {
        codeContainer.getCode().forEach(this::updateConstantTree);
    }

    /*
     * scan code line for items that need adding to the constant pool
     */
    private void updateConstantTree(CodeLine codeline) {
        if (codeline.hasMethod()) {
            addMethod(codeline);
        }

        if (codeline.hasField()) {
            addField(codeline);
        }
    }

    private void addMethod(CodeLine codeline) {
        constantTree.add(new MethodRefEntry(getOrCreateClassEntry(codeline), createMethodNameAndType(codeline)));
    }

    private void addField(CodeLine codeline) {
        constantTree.add(new FieldRefEntry(getOrCreateClassEntry(codeline), createFieldNameAndType(codeline)));
    }

    private NameAndTypeEntry createMethodNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(new Utf8Entry(codeline.getMethodName()), new Utf8Entry(codeline.getMethodSignature()));
    }

    private NameAndTypeEntry createFieldNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(new Utf8Entry(codeline.getField().getName()), new Utf8Entry(TypeMapper.map(codeline.getField().getType())));
    }

    private ClassEntry getOrCreateClassEntry(CodeLine codeline) {
        if (codeline.getRef() == Ref.SUPER) {
            if (superClass == null) {
                superClass = createClassEntry(beeClass.getSuperClass().getName());
            }
            return superClass;
        } else if (codeline.getRef() == Ref.THIS) {
            if (thisClass == null) {
                thisClass = createClassEntry(beeClass.getName());
            }
            return thisClass;
        }
        //TODO other cases
        throw new RuntimeException("shouldn't be here");
    }

    private ClassEntry createClassEntry(String name) {
        return new ClassEntry(new Utf8Entry(internalName(name)));
    }

    private String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }

}

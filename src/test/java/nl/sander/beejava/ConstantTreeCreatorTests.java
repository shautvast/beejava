package nl.sander.beejava;

import nl.sander.beejava.api.*;
import nl.sander.beejava.constantpool.entry.*;
import nl.sander.beejava.flags.FieldAccessFlag;
import nl.sander.beejava.flags.MethodAccessFlag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static nl.sander.beejava.api.CodeLine.line;
import static nl.sander.beejava.api.Opcode.*;
import static nl.sander.beejava.flags.ClassAccessFlag.PUBLIC;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantTreeCreatorTests {

    // creates simplest class possible and checks the tree, that the ConstantTreeCreator emits
    @Test // This is not a maintainable test
    public void testMethodRefEntryForSuperConstructor() {
        BeeClass classWithIntField = createEmptyClass();
        Set<NodeConstant> constantTree = new ConstantTreeCreator().createConstantTree(classWithIntField);
        assertEquals(1, constantTree.size());
        NodeConstant superConstructor = constantTree.iterator().next();

        assertEquals(ConstantMethodRef.class, superConstructor.getClass());
        ConstantMethodRef constantMethodRef = (ConstantMethodRef) superConstructor;

        Set<NodeConstant> methodRefEntryChildren = constantMethodRef.getChildren();
        assertEquals(2, methodRefEntryChildren.size());

        Iterator<NodeConstant> firstChildren = methodRefEntryChildren.iterator();
        NodeConstant child1 = firstChildren.next();
        assertEquals(ConstantClass.class, child1.getClass());
        ConstantClass constantClass = (ConstantClass) child1;

        Set<NodeConstant> classEntryChildren = constantClass.getChildren();
        assertEquals(1, classEntryChildren.size());
        NodeConstant child2 = classEntryChildren.iterator().next();

        assertEquals(ConstantUtf8.class, child2.getClass());
        ConstantUtf8 className = (ConstantUtf8) child2;
        assertEquals("java/lang/Object", className.getUtf8());

        NodeConstant child3 = firstChildren.next();
        assertEquals(ConstantNameAndType.class, child3.getClass());
        ConstantNameAndType constantNameAndType = (ConstantNameAndType) child3;

        Set<NodeConstant> nameAndTypeEntryChildren = constantNameAndType.getChildren();
        assertEquals(2, nameAndTypeEntryChildren.size());
        Iterator<NodeConstant> nameAndTypeChildrenIterator = nameAndTypeEntryChildren.iterator();

        NodeConstant child4 = nameAndTypeChildrenIterator.next();
        assertEquals(ConstantUtf8.class, child4.getClass());
        ConstantUtf8 name = (ConstantUtf8) child4;
        assertEquals("<init>", name.getUtf8());

        NodeConstant child5 = nameAndTypeChildrenIterator.next();
        assertEquals(ConstantUtf8.class, child5.getClass());
        ConstantUtf8 type = (ConstantUtf8) child5;
        assertEquals("()V", type.getUtf8());
    }

    private BeeClass createEmptyClass() {
        BeeConstructor constructor = BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlag.PUBLIC)
                .withCode(
                        line(0, LOAD, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(5, RETURN))
                .build();

        return BeeClass.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withConstructors(constructor) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    private BeeClass createClassWithIntField() {
        BeeField intField = BeeField.builder()
                .withAccessFlags(FieldAccessFlag.PRIVATE)
                .withType(int.class)
                .withName("intField")
                .build();

        BeeParameter intValueParameter = BeeParameter.create(int.class, "intValue");

        BeeConstructor constructor = BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlag.PUBLIC)
                .withFormalParameters(intValueParameter)
                .withCode(
                        line(0, LOAD, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(2, LOAD, Ref.THIS),
                        line(3, LOAD, intValueParameter),
                        line(4, PUT, intField),
                        line(5, RETURN))
                .build();

        return BeeClass.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("IntBean")
                .withSuperClass(Object.class)
                .withFields(intField)
                .withConstructors(constructor)
                .build();
    }


}

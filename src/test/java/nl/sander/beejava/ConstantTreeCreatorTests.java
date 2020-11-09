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
        BeeClass classWithIntField = TestData.emptyClass();
        Set<NodeConstant> constantTree = new ConstantTreeCreator().createConstantTree(classWithIntField);
        assertEquals(1, constantTree.size());
        NodeConstant superConstructor = constantTree.iterator().next();

        assertEquals(MethodRefEntry.class, superConstructor.getClass());
        MethodRefEntry methodRefEntry = (MethodRefEntry) superConstructor;

        Set<NodeConstant> methodRefEntryChildren = methodRefEntry.getChildren();
        assertEquals(2, methodRefEntryChildren.size());

        Iterator<NodeConstant> firstChildren = methodRefEntryChildren.iterator();
        NodeConstant child1 = firstChildren.next();
        assertEquals(ClassEntry.class, child1.getClass());
        ClassEntry classEntry = (ClassEntry) child1;

        Set<NodeConstant> classEntryChildren = classEntry.getChildren();
        assertEquals(1, classEntryChildren.size());
        NodeConstant child2 = classEntryChildren.iterator().next();

        assertEquals(Utf8Entry.class, child2.getClass());
        Utf8Entry className = (Utf8Entry) child2;
        assertEquals("java/lang/Object", className.getUtf8());

        NodeConstant child3 = firstChildren.next();
        assertEquals(NameAndTypeEntry.class, child3.getClass());
        NameAndTypeEntry nameAndTypeEntry = (NameAndTypeEntry) child3;

        Set<NodeConstant> nameAndTypeEntryChildren = nameAndTypeEntry.getChildren();
        assertEquals(2, nameAndTypeEntryChildren.size());
        Iterator<NodeConstant> nameAndTypeChildrenIterator = nameAndTypeEntryChildren.iterator();

        NodeConstant child4 = nameAndTypeChildrenIterator.next();
        assertEquals(Utf8Entry.class, child4.getClass());
        Utf8Entry name = (Utf8Entry) child4;
        assertEquals("<init>", name.getUtf8());

        NodeConstant child5 = nameAndTypeChildrenIterator.next();
        assertEquals(Utf8Entry.class, child5.getClass());
        Utf8Entry type = (Utf8Entry) child5;
        assertEquals("()V", type.getUtf8());
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

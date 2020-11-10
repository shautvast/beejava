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

public class CompilerTests {

    // creates simplest class possible and checks the tree, that the ConstantTreeCreator emits
    @Test // This is not a maintainable test
    public void testMethodRefEntryForSuperConstructor() {
        BeeClass classWithIntField = TestData.emptyClass();
        CompiledClass compiledClass = new Compiler().compile(classWithIntField);
        Set<ConstantPoolEntry> constantTree = compiledClass.getConstantTree();
        assertEquals(2, constantTree.size());
        ConstantPoolEntry superConstructor = constantTree.iterator().next();

        assertEquals(MethodRefEntry.class, superConstructor.getClass());
        MethodRefEntry methodRefEntry = (MethodRefEntry) superConstructor;

        Set<ConstantPoolEntry> methodRefEntryChildren = methodRefEntry.getChildren();
        assertEquals(2, methodRefEntryChildren.size());

        Iterator<ConstantPoolEntry> firstChildren = methodRefEntryChildren.iterator();
        ConstantPoolEntry child1 = firstChildren.next();
        assertEquals(ClassEntry.class, child1.getClass());
        ClassEntry classEntry = (ClassEntry) child1;

        Set<ConstantPoolEntry> classEntryChildren = classEntry.getChildren();
        assertEquals(1, classEntryChildren.size());
        ConstantPoolEntry child2 = classEntryChildren.iterator().next();

        assertEquals(Utf8Entry.class, child2.getClass());
        Utf8Entry className = (Utf8Entry) child2;
        assertEquals("java/lang/Object", className.getUtf8());

        ConstantPoolEntry child3 = firstChildren.next();
        assertEquals(NameAndTypeEntry.class, child3.getClass());
        NameAndTypeEntry nameAndTypeEntry = (NameAndTypeEntry) child3;

        Set<ConstantPoolEntry> nameAndTypeEntryChildren = nameAndTypeEntry.getChildren();
        assertEquals(2, nameAndTypeEntryChildren.size());
        Iterator<ConstantPoolEntry> nameAndTypeChildrenIterator = nameAndTypeEntryChildren.iterator();

        ConstantPoolEntry child4 = nameAndTypeChildrenIterator.next();
        assertEquals(Utf8Entry.class, child4.getClass());
        Utf8Entry name = (Utf8Entry) child4;
        assertEquals("<init>", name.getUtf8());

        ConstantPoolEntry child5 = nameAndTypeChildrenIterator.next();
        assertEquals(Utf8Entry.class, child5.getClass());
        Utf8Entry type = (Utf8Entry) child5;
        assertEquals("()V", type.getUtf8());
    }
}

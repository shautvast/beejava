package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * make sure tags were put in the code according to spec (§4.4)
 */
public class TagCorrectnessTest {
    @Test
    public void testSpec() {
        assertEquals(1, utf8().getTag());
        assertEquals(3, new IntegerEntry(0).getTag());
        assertEquals(4, new FloatEntry(0).getTag());
        assertEquals(5, new LongEntry(0).getTag());
        assertEquals(6, new DoubleEntry(0).getTag());
        assertEquals(7, classEntry().getTag());
        assertEquals(8, new StringEntry(utf8()).getTag());
        assertEquals(9, fieldRef().getTag());
        assertEquals(10, new MethodRefEntry(classEntry(), nameAndType()).getTag());
        assertEquals(11, new InterfaceMethodRefEntry(classEntry(), nameAndType()).getTag());
        assertEquals(12, nameAndType().getTag());
        assertEquals(15, new MethodHandleEntry(0).getTag()); //TODO
        assertEquals(16, new MethodTypeEntry(utf8()).getTag()); //TODO
        assertEquals(17, new DynamicEntry(0, nameAndType()).getTag()); //TODO
        assertEquals(18, new InvokeDynamicEntry(0, nameAndType()).getTag()); //TODO
        assertEquals(19, new ModuleEntry(utf8()).getTag());
        assertEquals(20, new PackageEntry(utf8()).getTag());

    }

    private FieldRefEntry fieldRef() {
        return new FieldRefEntry(classEntry(), nameAndType());
    }

    private NameAndTypeEntry nameAndType() {
        return new NameAndTypeEntry(utf8(), utf8());
    }


    private ClassEntry classEntry() {
        return new ClassEntry(utf8());
    }

    private Utf8Entry utf8() {
        return new Utf8Entry("");
    }
}

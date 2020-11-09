package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * make sure tags were put in the code according to spec (§4.4)
 */
public class TagCorrectnessTest {
    @Test
    public void testSpec() {
        assertEquals(7, classEntry().getTag());
        assertEquals(9, fieldRef().getTag());
        assertEquals(10, new ConstantMethodRef(classEntry(), nameAndType()).getTag());
        assertEquals(11, new ConstantInterfaceMethodRef(classEntry(), nameAndType()).getTag());
        assertEquals(8, new ConstantString(utf8()).getTag());
        assertEquals(3, new ConstantInteger(0).getTag());
        assertEquals(4, new ConstantFloat(0).getTag());
        assertEquals(5, new ConstantLong(0).getTag());
        assertEquals(6, new ConstantDouble(0).getTag());
        assertEquals(12, nameAndType().getTag());
        assertEquals(1, utf8().getTag());
        assertEquals(15, new ConstantMethodHandle(0).getTag()); //TODO
        assertEquals(16, new ConstantMethodType(utf8()).getTag()); //TODO
        assertEquals(17, new ConstantDynamic(0, nameAndType()).getTag()); //TODO
        assertEquals(18, new ConstantInvokeDynamic(0, nameAndType()).getTag()); //TODO
        assertEquals(19, new ConstantModule(utf8()).getTag());
        assertEquals(20, new ConstantPackage(utf8()).getTag());

    }

    private ConstantFieldRef fieldRef() {
        return new ConstantFieldRef(classEntry(), nameAndType());
    }

    private ConstantNameAndType nameAndType() {
        return new ConstantNameAndType(utf8(), utf8());
    }


    private ConstantClass classEntry() {
        return new ConstantClass(utf8());
    }

    private ConstantUtf8 utf8() {
        return new ConstantUtf8("");
    }
}

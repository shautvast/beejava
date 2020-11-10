package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantPoolEntryTest {

    private final ConstantPoolEntry entry = new ConstantPoolEntry() {
        @Override
        public byte[] getBytes() {
            return new byte[]{};
        }
    };

    @Test
    public void getSetIndex() {
        int value = 2;
        entry.setIndex(value);
        assertEquals(value, entry.getIndex());
    }

    @Test
    public void lowerByte() {
        assertEquals(1, entry.lowerByte(257));
    }

    @Test
    public void upperByte() {
        assertEquals(1, entry.upperByte(257));
    }
}
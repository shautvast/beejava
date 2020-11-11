package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FloatEntryTest {

    @Test
    public void test_getBytes() {
        assertArrayEquals(new byte[]{4, 0, 0, 0, 0}, new FloatEntry(0F).getBytes());
    }
}
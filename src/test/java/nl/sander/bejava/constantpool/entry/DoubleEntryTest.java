package nl.sander.bejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DoubleEntryTest {

    @Test
    public void test_getBytes() {
        assertArrayEquals(new byte[]{6, 64, 40, 0, 0, 0, 0, 0, 0}, new DoubleEntry(12D).getBytes());
    }
}
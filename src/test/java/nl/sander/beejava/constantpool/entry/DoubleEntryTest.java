package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DoubleEntryTest {

    @Test
    void getBytes() {
        assertArrayEquals(new byte[]{6, 64, 40, 0, 0, 0, 0, 0, 0}, new DoubleEntry(12D).getBytes());
    }
}
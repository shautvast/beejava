package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FloatEntryTest {

    @Test
    void getBytes() {
        assertArrayEquals(new byte[]{4, 0, 0, 0, 0}, new FloatEntry(0F).getBytes());
    }
}
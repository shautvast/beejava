package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IntegerEntryTest {

    @Test
    void getBytes() {
        assertArrayEquals(new byte[]{3, 18, 52, 86, 120}, new IntegerEntry(0x12345678).getBytes());
    }
}
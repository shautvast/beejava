package nl.sander.bejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class LongEntryTest {

    @Test
    public void test_getBytes() {
        assertArrayEquals(new byte[]{5, 18, 52, 86, 120, -102, -68, -34, -16}, new LongEntry(0x123456789ABCDEF0L).getBytes());
    }
}
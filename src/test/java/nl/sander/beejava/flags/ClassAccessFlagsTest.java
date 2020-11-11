package nl.sander.beejava.flags;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassAccessFlagsTest {
    @Test
    public void test_mustOr1Value() {
        assertEquals(1, AccessFlags.combine(List.of(ClassAccessFlags.PUBLIC)));
    }

    @Test
    public void test_mustOr2Values() {
        assertEquals(17, AccessFlags.combine(List.of(ClassAccessFlags.PUBLIC, ClassAccessFlags.FINAL)));
    }



}

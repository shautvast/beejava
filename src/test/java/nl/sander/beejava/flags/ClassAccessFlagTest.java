package nl.sander.beejava.flags;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassAccessFlagTest {
    @Test
    public void mustOr1Value() {
        assertEquals(1, ClassAccessFlag.getSum(Collections.singletonList(ClassAccessFlag.PUBLIC)));
    }

    @Test
    public void mustOr2Values() {
        assertEquals(17, ClassAccessFlag.getSum(Arrays.asList(ClassAccessFlag.PUBLIC, ClassAccessFlag.FINAL)));
    }



}

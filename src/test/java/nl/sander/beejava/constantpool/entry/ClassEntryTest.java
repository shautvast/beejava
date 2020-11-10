package nl.sander.beejava.constantpool.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class ClassEntryTest {
    @Test
    public void testGetChildren() {
        Utf8Entry name = new Utf8Entry("");
        assertSame(new ClassEntry(name).getChildren().iterator().next(), name);
    }
}

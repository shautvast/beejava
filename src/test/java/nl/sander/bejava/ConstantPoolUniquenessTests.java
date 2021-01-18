package nl.sander.bejava;

import nl.sander.bejava.api.BeSource;
import nl.sander.bejava.constantpool.ConstantPool;
import nl.sander.bejava.constantpool.entry.ClassEntry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantPoolUniquenessTests {
    @Test
    public void test() throws Exception {
        // Arrange
        BeSource someClass = SourceCompiler.compile("""
                class public nl.sander.beejava.test.EmptyBean
                constructor public()
                  INVOKE this.super()
                  RETURN
                """);

        // Act
        CompiledClass compiledClass = Compiler.compile(someClass);
        ConstantPool constantPool = new ConstantPoolCreator().createConstantPool(compiledClass.getConstantTree());

        // Assert
        List<ClassEntry> refsToSystem = constantPool.stream()
                .filter(cpe -> cpe instanceof ClassEntry)
                .map(cpe -> (ClassEntry) cpe)
                .filter(ce -> ce.getName().equals("java/lang/System"))
                .collect(Collectors.toList());

        assertEquals(0, refsToSystem.size());
    }
}

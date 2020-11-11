package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ClassEntry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantPoolUniquenessTests {
    @Test
    public void test() {
        // Arrange
        BeeSource someClass = TestData.createClassWithTwoReferencesToSomeClass();

        // Act
        CompiledClass compiledClass = Compiler.compile(someClass);
        ConstantPool constantPool = new ConstantPoolCreator().createConstantPool(compiledClass.getConstantTree());

        // Assert
        List<ClassEntry> refsToSystem = constantPool.stream()
                .filter(cpe -> cpe instanceof ClassEntry)
                .map(cpe -> (ClassEntry) cpe)
                .filter(ce -> ce.getName().equals("java/lang/System"))
                .collect(Collectors.toList());

        assertEquals(1, refsToSystem.size());
    }
}

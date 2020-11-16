package nl.sander.beejava.e2e;

import nl.sander.beejava.BytecodeGenerator;
import nl.sander.beejava.CompiledClass;
import nl.sander.beejava.Compiler;
import nl.sander.beejava.TestData;
import nl.sander.beejava.api.BeeSource;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A rather simple case, still meaningful nonetheless.
 * Green means class can be loaded, so the class file structure is valid.
 * The EmptyBean class just contains a default constructor.
 */
public class EmptyBeanTest {
    @Test
    public void testEmptyBean() throws Exception {
        // Arrange
        BeeSource emptyClass = TestData.createEmptyClass();

        // Act
        CompiledClass compiledClass = Compiler.compile(emptyClass);
        byte[] bytecode = BytecodeGenerator.generate(compiledClass);

        ByteClassLoader classLoader = new ByteClassLoader();
        classLoader.setByteCode("nl.sander.beejava.test.EmptyBean", bytecode);

        // Assert
        Constructor<?> constructor = classLoader.loadClass("nl.sander.beejava.test.EmptyBean").getConstructor();
        assertNotNull(constructor);

        Object instance = constructor.newInstance();
        assertNotNull(instance);

    }
}

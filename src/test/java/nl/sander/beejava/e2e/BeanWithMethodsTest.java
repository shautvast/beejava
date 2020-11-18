package nl.sander.beejava.e2e;

import nl.sander.beejava.BytecodeGenerator;
import nl.sander.beejava.CompiledClass;
import nl.sander.beejava.Compiler;
import nl.sander.beejava.TestData;
import nl.sander.beejava.api.BeeSource;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A rather simple case, still meaningful nonetheless.
 * Green means class can be loaded, so the class file structure is valid.
 * The EmptyBean class just contains a default constructor.
 */
public class BeanWithMethodsTest {
    @Test
    public void testEmptyBean() throws Exception {
        // Arrange
        BeeSource emptyClass = TestData.createClassWithTwoReferencesToSomeClass();

        // Act
        CompiledClass compiledClass = Compiler.compile(emptyClass);
        byte[] bytecode = BytecodeGenerator.generate(compiledClass);

        ByteClassLoader classLoader = new ByteClassLoader();
        classLoader.setByteCode("nl.sander.beejava.test.ClassWithReferences", bytecode);

        // Assert
        Class<?> classWithReferences = classLoader.loadClass("nl.sander.beejava.test.ClassWithReferences");
        assertNotNull(classWithReferences);

        Method[] methods = classWithReferences.getDeclaredMethods();
        assertEquals(2, methods.length);
        assertEquals("print1", methods[0].getName());
        assertTrue(Modifier.isPublic(methods[0].getModifiers()));
        assertEquals(0,methods[0].getParameterCount());

        assertEquals("print2", methods[1].getName()); // ordering may cause failures
        assertTrue(Modifier.isPublic(methods[1].getModifiers()));
        assertEquals(0,methods[1].getParameterCount());

    }
}

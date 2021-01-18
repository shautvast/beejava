package nl.sander.beejava.e2e;

import nl.sander.beejava.Compiler;
import nl.sander.beejava.*;
import nl.sander.beejava.api.BeeSource;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Proves ability to compile opcodes for GET, LOAD, INVOKE, RETURN.
 * Secondly the usage of method refs, constants.
 */
public class BeanWithMethodsTest {
    @Test
    public void testEmptyBean() throws Exception {
        // Arrange
        BeeSource printer = SourceCompiler.compile("""           
                class nl.sander.beejava.test.ClassWithReferences
                constructor public()
                  INVOKE this.super()V
                  RETURN
                method public print1()
                  GET java.lang.System.out
                  LOAD "1"
                  INVOKE java/io/PrintStream.println(Ljava/lang/String;)V
                  RETURN
                method public print2()
                  GET java.lang.System.out
                  LOAD int 2
                  INVOKE java/io/PrintStream.println(I)V
                  RETURN
                """);

        // Act
        OpcodeTranslator.translate(printer);
        CompiledClass compiledClass = Compiler.compile(printer);
        byte[] bytecode = BytecodeGenerator.generate(compiledClass);

        ByteClassLoader classLoader = new ByteClassLoader();
        String className = "nl.sander.beejava.test.ClassWithReferences";
        classLoader.setByteCode(className, bytecode);

        // Assert
        Class<?> classWithReferences = classLoader.loadClass(className);
        assertNotNull(classWithReferences);

        Method[] methods = classWithReferences.getDeclaredMethods();
        assertEquals(2, methods.length);
        assertEquals("print1", methods[0].getName());
        assertTrue(Modifier.isPublic(methods[0].getModifiers()));
        assertEquals(0, methods[0].getParameterCount());

        assertEquals("print2", methods[1].getName()); // ordering may cause failures
        assertTrue(Modifier.isPublic(methods[1].getModifiers()));
        assertEquals(0, methods[1].getParameterCount());

    }
}

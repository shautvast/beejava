package nl.sander.bejava.e2e;

import nl.sander.bejava.Compiler;
import nl.sander.bejava.*;
import nl.sander.bejava.api.BeSource;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Proves ability to compile opcodes for GET, LOAD, INVOKE, RETURN.
 * Secondly the usage of method refs, constants.
 */
public class BeanWithMethodsTest {
    @Test
    public void testEmptyBean() throws Exception {
        // Arrange
        BeSource printer = SourceCompiler.compile("""           
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

        List<Method> methods = Arrays.asList(classWithReferences.getDeclaredMethods());
        methods.sort(Comparator.comparing(Method::getName));
        assertEquals(2, methods.size());
        assertEquals("print1", methods.get(0).getName());
        assertTrue(Modifier.isPublic(methods.get(0).getModifiers()));
        assertEquals(0, methods.get(0).getParameterCount());

        assertEquals("print2", methods.get(1).getName()); // ordering may cause failures
        assertTrue(Modifier.isPublic(methods.get(1).getModifiers()));
        assertEquals(0, methods.get(1).getParameterCount());

    }
}

package nl.sander.beejava.e2e;

import nl.sander.beejava.OverallCompiler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Hello world test for bejava. Proves ability to compile bejava-lang to valid bytecode.
 */
public class EmptyBeanTest {
    @Test
    public void testEmptyBean() throws Exception {
        // Act
        byte[] bytecode = OverallCompiler.compile("""
                class public nl.sander.beejava.test.EmptyBean
                constructor public()
                  INVOKE this.super()V
                  RETURN
                """);


        ByteClassLoader classLoader = new ByteClassLoader();
        classLoader.setByteCode("nl.sander.beejava.test.EmptyBean", bytecode);

        // Assert
        Constructor<?> constructor = classLoader.loadClass("nl.sander.beejava.test.EmptyBean").getConstructor();
        assertNotNull(constructor);

        Object instance = constructor.newInstance();
        assertNotNull(instance);
    }
}

package nl.sander.beejava.e2e;

import nl.sander.beejava.BytecodeGenerator;
import nl.sander.beejava.CompiledClass;
import nl.sander.beejava.Compiler;
import nl.sander.beejava.TestData;
import nl.sander.beejava.api.BeeConstructor;
import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.api.Version;
import nl.sander.beejava.flags.MethodAccessFlags;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static nl.sander.beejava.api.CodeLine.line;
import static nl.sander.beejava.api.Opcode.*;
import static nl.sander.beejava.flags.ClassAccessFlags.PUBLIC;
import static nl.sander.beejava.flags.ClassAccessFlags.SUPER;
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
        BeeSource emptyClass = createEmptyClass();

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

    private BeeSource createEmptyClass() throws ClassNotFoundException {
        return BeeSource.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC, SUPER)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withConstructors(createDefaultConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    private BeeConstructor createDefaultConstructor() throws ClassNotFoundException {
        return BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlags.PUBLIC)
                .withCode(
                        line(0, LD_VAR, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(5, RETURN))
                .build();
    }
}

package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.NameAndTypeEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TypeMapperTest {

    @Test
    public void test_int() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(int.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("I", fieldEntry.getType());
    }

    @Test
    public void test_double() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(double.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("D", fieldEntry.getType());
    }

    @Test
    public void test_float() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(float.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("F", fieldEntry.getType());
    }

    @Test
    public void test_byte() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(byte.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("B", fieldEntry.getType());
    }

    @Test
    public void test_short() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(short.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("S", fieldEntry.getType());
    }

    @Test
    public void test_long() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(long.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("J", fieldEntry.getType());
    }

    @Test
    public void test_char() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(char.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("C", fieldEntry.getType());
    }

    @Test
    public void test_boolean() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(boolean.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("Z", fieldEntry.getType());
    }

    @Test
    public void test_Object() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(Object.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("Ljava/lang/Object;", fieldEntry.getType());
    }

    @Test
    public void test_int_array() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(int[].class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("[I", fieldEntry.getType());
    }

    @Test
    public void test_Object_array() throws ClassNotFoundException {
        // Arrange
        BeeSource beeSource = TestData.createClassWithField(String[].class);

        // Act
        ConstantPool constantPool = createConstantPool(beeSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("[Ljava/lang/String;", fieldEntry.getType());
    }

    private ConstantPool createConstantPool(BeeSource beeSource) {
        CompiledClass compiledClass = Compiler.compile(beeSource);
        return ConstantPoolCreator.create(compiledClass.getConstantTree());
    }

    private NameAndTypeEntry getFieldNameAndType(ConstantPool constantPool) {
        return constantPool.stream()
                .filter(cpe -> cpe instanceof NameAndTypeEntry)
                .map(NameAndTypeEntry.class::cast)
                .filter(nate -> nate.getName().equals("field"))
                .findAny().orElseGet(() -> fail("'field' not found"));
    }
}

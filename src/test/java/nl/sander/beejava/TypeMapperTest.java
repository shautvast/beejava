package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.NameAndTypeEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TypeMapperTest {

    @Test
    public void test_int() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(int.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("I", fieldEntry.getType());
    }

    @Test
    public void test_double() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(double.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("D", fieldEntry.getType());
    }

    @Test
    public void test_float() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(float.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("F", fieldEntry.getType());
    }

    @Test
    public void test_byte() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(byte.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("B", fieldEntry.getType());
    }

    @Test
    public void test_short() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(short.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("S", fieldEntry.getType());
    }

    @Test
    public void test_long() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(long.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("J", fieldEntry.getType());
    }

    @Test
    public void test_char() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(char.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("C", fieldEntry.getType());
    }

    @Test
    public void test_boolean() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(boolean.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("Z", fieldEntry.getType());
    }

    @Test
    public void test_Object() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(Object.class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("Ljava/lang/Object", fieldEntry.getType());
    }

    @Test
    public void test_int_array() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(int[].class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("[I", fieldEntry.getType());
    }

    @Test
    public void test_Object_array() {
        // Arrange
        BeeClass beeClass = TestData.createClassWithField(String[].class);

        // Act
        ConstantPool constantPool = createConstantPool(beeClass);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("[Ljava/lang/String;", fieldEntry.getType());
    }

    private ConstantPool createConstantPool(BeeClass beeClass) {
        CompiledClass compiledClass = Compiler.compile(beeClass);
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

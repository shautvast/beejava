package nl.sander.bejava;

import nl.sander.bejava.api.BeSource;
import nl.sander.bejava.constantpool.ConstantPool;
import nl.sander.bejava.constantpool.entry.NameAndTypeEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TypeMapperTest {

    @Test
    public void test_int() {
        // Arrange
        BeSource beSource = createClassWithField(int.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("I", fieldEntry.getType());
    }

    @Test
    public void test_double() {
        // Arrange
        BeSource beSource = createClassWithField(double.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("D", fieldEntry.getType());
    }

    @Test
    public void test_float() {
        // Arrange
        BeSource beSource = createClassWithField(float.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("F", fieldEntry.getType());
    }

    @Test
    public void test_byte() {
        // Arrange
        BeSource beSource = createClassWithField(byte.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("B", fieldEntry.getType());
    }

    @Test
    public void test_short() {
        // Arrange
        BeSource beSource = createClassWithField(short.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("S", fieldEntry.getType());
    }

    @Test
    public void test_long() {
        // Arrange
        BeSource beSource = createClassWithField(long.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("J", fieldEntry.getType());
    }

    @Test
    public void test_char() {
        // Arrange
        BeSource beSource = createClassWithField(char.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("C", fieldEntry.getType());
    }

    @Test
    public void test_boolean() {
        // Arrange
        BeSource beSource = createClassWithField(boolean.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("Z", fieldEntry.getType());
    }

    @Test
    public void test_Object() {
        // Arrange
        BeSource beSource = createClassWithField(Object.class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("Ljava/lang/Object;", fieldEntry.getType());
    }

    @Test
    public void test_int_array() {
        // Arrange
        BeSource beSource = createClassWithField(int[].class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("[I", fieldEntry.getType());
    }

    @Test
    public void test_Object_array() {
        // Arrange
        BeSource beSource = createClassWithField(String[].class);

        // Act
        ConstantPool constantPool = createConstantPool(beSource);

        // Assert
        NameAndTypeEntry fieldEntry = getFieldNameAndType(constantPool);
        assertEquals("[Ljava/lang/String;", fieldEntry.getType());
    }

    private ConstantPool createConstantPool(BeSource beSource) {
        CompiledClass compiledClass = Compiler.compile(beSource);
        return ConstantPoolCreator.create(compiledClass.getConstantTree());
    }

    private NameAndTypeEntry getFieldNameAndType(ConstantPool constantPool) {
        return constantPool.stream()
                .filter(cpe -> cpe instanceof NameAndTypeEntry)
                .map(NameAndTypeEntry.class::cast)
                .filter(nate -> nate.getName().equals("field"))
                .findAny().orElseGet(() -> fail("'field' not found"));
    }

    private BeSource createClassWithField(Class<?> fieldType) {
        String template = """           
                class com.acme.SimpleBean(V15)
                field private %s field
                constructor public(%s arg)
                  INVOKE this.super()V
                  LOAD arg
                  PUT this.field
                  RETURN
                """;

        return SourceCompiler.compile(String.format(template, fieldType.getName(), fieldType.getName()));
    }
}

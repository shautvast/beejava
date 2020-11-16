package nl.sander.beejava;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BytecodeGeneratorTests {
    @Test
    public void testEmpty() throws IOException, ClassNotFoundException {
        byte[] bytecode = BytecodeGenerator.generate(Compiler.compile(TestData.createEmptyClass()));
        File dir = new File("target/nl/sander/beejava/test");
        dir.mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, "EmptyBean.class"))) {
            outputStream.write(bytecode);
        }
    }

    @Test
    public void testInterface() throws ClassNotFoundException {
        BytecodeGenerator.generate(Compiler.compile(TestData.emptyClassWithInterface()));
    }

    @Test
    public void testFields() throws ClassNotFoundException {
        BytecodeGenerator.generate(Compiler.compile(TestData.createClassWithField(int.class)));
    }
}

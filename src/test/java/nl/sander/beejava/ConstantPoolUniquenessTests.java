package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ClassEntry;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantPoolUniquenessTests {
    @Test
    public void test() throws Exception {
        // Arrange
        BeeSource someClass = TestData.createEmptyClass();

        // Act
        CompiledClass compiledClass = Compiler.compile(someClass);
        ConstantPool constantPool = new ConstantPoolCreator().createConstantPool(compiledClass.getConstantTree());

        // Assert
        List<ClassEntry> refsToSystem = constantPool.stream()
                .filter(cpe -> cpe instanceof ClassEntry)
                .map(cpe -> (ClassEntry) cpe)
                .filter(ce -> ce.getName().equals("java/lang/System"))
                .collect(Collectors.toList());

        assertEquals(0, refsToSystem.size());


        byte[] bytecode = BytecodeGenerator.generate(compiledClass);

        int x = 1;
        for (ConstantPoolEntry e : constantPool) {
            System.out.println((x++) + ":" + e);
        }

        x = 1;
        for (ConstantPoolEntry e : constantPool) {
            System.out.print((x++) + ":");
            printBytes(e.getBytes());
        }


        File dir = new File("target/nl/sander/beejava/test");
        dir.mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, "EmptyBean.class"))) {
            outputStream.write(bytecode);
        }
        printBytes2(bytecode);
    }

    //TODO remove
    private void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print(String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0') +  " ");
        }
        System.out.println();
    }

    private void printBytes2(byte[] bytes) {
        int count = 0;
        for (byte b : bytes) {
            System.out.print(String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0') + (count % 2 == 0 ? "" : " "));
            count += 1;
            if (count > 15) {
                count = 0;
                System.out.println();
            }
        }
    }
}

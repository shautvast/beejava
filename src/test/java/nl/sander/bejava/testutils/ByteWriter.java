package nl.sander.bejava.testutils;

import nl.sander.bejava.constantpool.ConstantPool;
import nl.sander.bejava.constantpool.entry.ConstantPoolEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteWriter {

    public static void printBytes(ConstantPool constantPool) {
        int cpIndex = 1;
        for (ConstantPoolEntry e : constantPool) {
            cpIndex += 1;
            System.out.print(cpIndex + ":");
            for (byte b : e.getBytes()) {
                System.out.print(String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0') + " ");
            }
            System.out.println();
        }
    }

    public static String printBytes(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        int count = 0;
        for (byte b : bytes) {
            s.append(String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0'));
            s.append(count % 2 == 0 ? "" : " ");
            count += 1;
            if (count > 15) {
                count = 0;
                s.append(String.format("%n"));
            }
        }
        return s.toString();
    }

    public static void writeToFile(byte[] bytecode, String dirName, String className) {
        File dir = new File(dirName);
        dir.mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, className))) {
            outputStream.write(bytecode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

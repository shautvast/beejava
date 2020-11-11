package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.util.ByteBuf;

import java.util.ArrayList;

public class ConstantPool extends ArrayList<ConstantPoolEntry>{

    public byte[] getBytes() {
        ByteBuf bytes = new ByteBuf();
        forEach(entry -> bytes.addU8(entry.getBytes()));
        return bytes.toBytes();
    }

    /**
     * get the length for constant_pool_count in the class file
     *
     * 4.1. The ClassFile Structure
     * The value of the constant_pool_count item is equal to the number of entries in the constant_pool table plus one
     */
    public int getLength() {
        return size() + 1;
    }
}

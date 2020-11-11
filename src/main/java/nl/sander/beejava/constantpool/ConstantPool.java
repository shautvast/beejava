package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.util.ByteBuf;

import java.util.ArrayList;

public class ConstantPool extends ArrayList<ConstantPoolEntry>{

    /**
     * Add the bytes for all entries to the given ByteBuf
     * @param buf the buffer that will contain the bytes for the constant pool
     */
    public void addTo(ByteBuf buf) {
        forEach(entry -> buf.addU8(entry.getBytes()));
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

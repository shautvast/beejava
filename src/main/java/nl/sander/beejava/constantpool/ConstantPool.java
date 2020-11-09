package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.util.ByteBuf;

import java.util.ArrayList;

public class ConstantPool extends ArrayList<ConstantPoolEntry>{

    public int getIndex(ConstantPoolEntry entry) {
        for (int i = 0; i < size(); i++) {
            if (get(i) == entry) {
                return i + 1;
            }
        }
        return -1;
    }

    public byte[] getBytes() {
        ByteBuf bytes = new ByteBuf();
        forEach(entry -> bytes.addU8(entry.getBytes()));
        return bytes.toBytes();
    }

    /**
     * get the length +1
     */
    public int getLength() {
        return size() + 1;
    }
}

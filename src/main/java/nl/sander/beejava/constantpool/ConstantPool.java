package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.NodeConstant;
import nl.sander.beejava.util.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ConstantPool {
    private final List<NodeConstant> entries = new ArrayList<>();

    public int getIndex(NodeConstant entry) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i) == entry) {
                return i + 1;
            }
        }
        return -1;
    }

    public void add(NodeConstant entry) {
        entries.add(entry);
    }

    public byte[] getBytes() {
        ByteBuf bytes = new ByteBuf();
        entries.forEach(entry -> bytes.addU8(entry.getBytes()));
        return bytes.toBytes();
    }

    /**
     * get the length +1
     */
    public int getLength() {
        return entries.size() + 1;
    }
}
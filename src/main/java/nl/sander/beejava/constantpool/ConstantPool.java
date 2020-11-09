package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.NodeConstant;
import nl.sander.beejava.util.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ConstantPool extends ArrayList<NodeConstant>{

    public int getIndex(NodeConstant entry) {
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

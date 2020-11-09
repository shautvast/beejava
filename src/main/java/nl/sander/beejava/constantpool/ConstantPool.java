package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.NodeConstant;

import java.util.ArrayList;
import java.util.List;

public class ConstantPool {
    private final List<NodeConstant> entries=new ArrayList<>();

    public int getIndex(NodeConstant entry){
        for (int i=0; i<entries.size(); i++){
            if (entries.get(i)==entry){
                return i+1;
            }
        }
        return -1;
    }

    public void add(NodeConstant entry){
        entries.add(entry);
    }

    public byte[] getBytes() {
        return new byte[]{};
    }
}

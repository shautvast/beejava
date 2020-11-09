package nl.sander.beejava.constantpool;

import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;

import java.util.ArrayList;
import java.util.List;

public class ConstantPool {
    private final List<ConstantPoolEntry> entries=new ArrayList<>();

    public int getIndex(ConstantPoolEntry entry){
        for (int i=0; i<entries.size(); i++){
            if (entries.get(i)==entry){
                return i+1;
            }
        }
        return -1;
    }

    public void add(int index, ConstantPoolEntry entry){
        entries.add(index, entry);
    }

    public void add(ConstantPoolEntry entry){
        entries.add(entry);
    }

    public byte[] getBytes() {
        return new byte[]{};
    }
}

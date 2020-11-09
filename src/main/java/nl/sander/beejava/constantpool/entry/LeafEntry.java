package nl.sander.beejava.constantpool.entry;

import java.util.Collections;
import java.util.Set;

public class LeafEntry extends ConstantPoolEntry {
    @Override
    public Set<ConstantPoolEntry> getChildren() {
        return Collections.emptySet();
    }
}

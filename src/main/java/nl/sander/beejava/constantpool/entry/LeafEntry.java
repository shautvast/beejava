package nl.sander.beejava.constantpool.entry;

import java.util.Collections;
import java.util.Set;

/**
 * Is a constant without children
 */
public abstract class LeafEntry extends ConstantPoolEntry {
    @Override
    public Set<ConstantPoolEntry> getChildren() {
        return Collections.emptySet();
    }
}

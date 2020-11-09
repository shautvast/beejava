package nl.sander.beejava.constantpool.entry;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class ConstantPoolEntry {
    protected final Set<ConstantPoolEntry> children;
    private int index;

    protected ConstantPoolEntry(ConstantPoolEntry... children) {
        this.children = new LinkedHashSet<>();
        this.children.addAll(Arrays.asList(children)); // java8 way destroys order, not desastrous, but I like to preserve it.
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<ConstantPoolEntry> getChildren() {
        return children;
    }
}

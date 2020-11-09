package nl.sander.beejava.constantpool.entry;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class NodeConstant {
    protected final Set<NodeConstant> children; // TODO decide whether or not to keep this. Could also make getChildren abstract and make it return a new list every time
                                                     // TODO to save storage. getChildren should only be called once, so it is probably more efficient like that
    private int index; // the index of the entry is not known until after creation of the complete tree, so cannot be final, but it should not be updated.

    protected NodeConstant(NodeConstant... children) {
        this.children = new LinkedHashSet<>();
        this.children.addAll(Arrays.asList(children)); // java8 way destroys order, not desastrous, but I like to preserve it.
    }

    /*
     * The tag that indicates the entry type in the bytecode.
     * §4.4 Table 4.4-A. Constant pool tags
     */
    public abstract int getTag();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<NodeConstant> getChildren() {
        return children;
    }

}

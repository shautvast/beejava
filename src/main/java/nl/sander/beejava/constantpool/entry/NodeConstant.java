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

    /**
     * return the bytes that end up in the class file.
     */
    public abstract byte[] getBytes();

    /**
     * Convenience for test. The TAG is always the first byte of the getBytes array
     * see §4.4 Table 4.4-A. Constant pool tags
     *
     * @return a byte indicating the type of the constant
     */
    byte getTag(){
        return getBytes()[0];
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<NodeConstant> getChildren() {
        return children;
    }

    /**
     * returns lower 8 bits as byte
     *
     * @param u16 is assumed to be 16 bits unsigned integer
     * @return lower 8 bits as byte
     */
    protected byte lowerByte(int u16) {
        return (byte) (u16 & 0xFF);
    }

    /**
     * returns upper 8 bits as byte
     *
     * @param u16 is assumed to be 16 bits unsigned integer
     * @return upper 8 bits as byte
     */
    protected byte upperByte(int u16) {
        return (byte) (u16 << 8);
    }

    protected byte getByte(long bits, int positions) {
        return (byte) ((bits >>> positions) & 0xFF);
    }
}

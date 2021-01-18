package nl.sander.beejava.constantpool.entry;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class ConstantPoolEntry {
    protected final Set<ConstantPoolEntry> children; // TODO decide whether or not to keep this. Could also make getChildren abstract and make it return a new list every time
    // TODO to save storage. getChildren should only be called once, so it is probably more efficient like that
    private int index; // the index of the entry is not known until after creation of the complete tree, so cannot be final, but it should not be updated.

    protected ConstantPoolEntry(ConstantPoolEntry... children) {
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
    byte getTag() {
        return getBytes()[0];
    }

    /**
     * The output of this ends up in the class file as the constantpool index of the entry.
     *
     * @return the cp index of the entry.
     */
    public int getIndex() {
        return index;
    }

    /**
     * With this the ConstantPoolCreator sets the calculated index.
     * @param index the cp index to assign to the entry
     */
    public void setIndex(int index) {
        this.index = index;
    }

    public Set<ConstantPoolEntry> getChildren() {
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
        return (byte) (u16 >>> 8);
    }

    /**
     * get the Nth byte in an "array" of bits encoded in a long (i64). Used to create the stream representation of 64bit numbers
     *
     * @param bits     a long in which a long or a double is encoded.
     * @param position the index of the byte (0..7) in the array of bits
     * @return the Nth byte in the long
     */
    protected byte getByte(long bits, int position) {
        if (position > 0 && position < 8) {
            return (byte) ((bits >>> (position * 8)) & 0xFF);
        } else {
            throw new IllegalArgumentException("position must be 0..7");
        }

    }

}

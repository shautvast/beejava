package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class LongEntry extends LeafEntry {

    private static final byte TAG = 5;
    private final long longVal;

    public LongEntry(long longVal) {
        this.longVal = longVal;
    }

    @Override
    public String toString() {
        return "Long\t\t" + longVal;
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG, getByte(longVal, 7), getByte(longVal, 6), getByte(longVal, 5),
                getByte(longVal, 4), getByte(longVal, 3), getByte(longVal, 2),
                getByte(longVal, 1), (byte) (longVal & 0xFF)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongEntry longEntry = (LongEntry) o;
        return longVal == longEntry.longVal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longVal);
    }
}

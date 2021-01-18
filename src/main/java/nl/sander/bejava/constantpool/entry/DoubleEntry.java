package nl.sander.bejava.constantpool.entry;

import java.util.Objects;

public class DoubleEntry extends LeafEntry {
    private static final byte TAG = 6;
    private final double doubleVal;

    public DoubleEntry(double doubleVal) {
        this.doubleVal = doubleVal;
    }

    @Override
    public byte[] getBytes() {
        long bits = Double.doubleToRawLongBits(doubleVal);
        return new byte[]{TAG, getByte(bits, 7), getByte(bits, 6), getByte(bits, 5),
                getByte(bits, 4),
                getByte(bits, 3), getByte(bits, 2), getByte(bits, 1), (byte) (bits & 0xFF)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (DoubleEntry) o;
        return Double.compare(that.doubleVal, doubleVal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doubleVal);
    }

    @Override
    public String toString() {
        return "Double\t\t" +doubleVal;
    }
}

package nl.sander.beejava.constantpool.entry;

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
        return new byte[]{TAG, getByte(bits, 56), getByte(bits, 48), getByte(bits, 40), getByte(bits, 32),
                getByte(bits, 24), getByte(bits, 16), getByte(bits, 8), (byte) (bits & 0xFF)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleEntry that = (DoubleEntry) o;
        return Double.compare(that.doubleVal, doubleVal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doubleVal);
    }
}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class FloatEntry extends LeafEntry {
    private static final byte TAG = 4;

    private final float floatVal;

    public FloatEntry(String floatVal) {
        this.floatVal = Float.parseFloat(floatVal);
    }

    public FloatEntry(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public String toString() {
        return "Float\t\t" + floatVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatEntry that = (FloatEntry) o;
        return Float.compare(that.floatVal, floatVal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floatVal);
    }

    @Override
    public byte[] getBytes() {
        long bits = Float.floatToRawIntBits(floatVal);
        return new byte[]{TAG, (byte) (bits >>> 24), (byte) (bits >>> 16), (byte) (bits >>> 8), (byte) (bits & 0xFF)};
    }
}

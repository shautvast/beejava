package nl.sander.beejava.constantpool.entry;

public class FloatEntry extends LeafConstant {
    private static final byte TAG = 4;

    private final float floatVal;

    public FloatEntry(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public String toString() {
        return "FloatEntry{" +
                "floatVal=" + floatVal +
                '}';
    }

    @Override
    public byte[] getBytes() {
        long bits = Float.floatToRawIntBits(floatVal);
        return new byte[]{TAG, (byte) (bits >>> 24), (byte) (bits >>> 16), (byte) (bits >>> 8), (byte) (bits & 0xFF)};
    }
}

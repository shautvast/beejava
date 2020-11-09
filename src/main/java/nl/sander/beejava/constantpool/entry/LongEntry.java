package nl.sander.beejava.constantpool.entry;

public class LongEntry extends LeafConstant {

    private static final byte TAG = 5;
    private final long longVal;

    public LongEntry(long longVal) {
        this.longVal = longVal;
    }

    @Override
    public String toString() {
        return "LongEntry{" +
                "longVal=" + longVal +
                '}';
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG, getByte(longVal, 56), getByte(longVal, 48), getByte(longVal, 40), getByte(longVal, 32),
                getByte(longVal, 24), getByte(longVal, 16), getByte(longVal, 8), (byte) (longVal & 0xFF)};
    }


}

package nl.sander.beejava.constantpool.entry;

public class ConstantLong extends LeafConstant {

    private static final byte TAG = 5;
    private final long longVal;

    public ConstantLong(long longVal) {
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
        return new byte[]{TAG, rshift(longVal, 56), rshift(longVal, 48), rshift(longVal, 40), rshift(longVal, 32),
                rshift(longVal, 24), rshift(longVal, 16), rshift(longVal, 8), (byte) (longVal & 0xFF)};
    }


}

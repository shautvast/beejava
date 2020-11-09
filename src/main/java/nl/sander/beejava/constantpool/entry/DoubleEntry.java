package nl.sander.beejava.constantpool.entry;

public class DoubleEntry extends LeafConstant {
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

}

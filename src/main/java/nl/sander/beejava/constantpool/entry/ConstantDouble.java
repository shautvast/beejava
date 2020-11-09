package nl.sander.beejava.constantpool.entry;

public class ConstantDouble extends LeafConstant {
    private static final byte TAG = 6;
    private final double doubleVal;

    public ConstantDouble(double doubleVal) {
        this.doubleVal = doubleVal;
    }

    @Override
    public byte[] getBytes() {
        long bits = Double.doubleToRawLongBits(doubleVal);
        return new byte[]{TAG, rshift(bits, 56), rshift(bits, 48), rshift(bits, 40), rshift(bits, 32),
                rshift(bits, 24), rshift(bits, 16), rshift(bits, 8), (byte) (bits & 0xFF)};
    }

}

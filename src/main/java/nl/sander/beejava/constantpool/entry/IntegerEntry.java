package nl.sander.beejava.constantpool.entry;

public class IntegerEntry extends LeafConstant {
    private static final byte TAG = 3;

    private final int intVal;

    public IntegerEntry(int integer) {
        this.intVal = integer;
    }

    @Override
    public String toString() {
        return "IntEntry{" +
                "intVal=" + intVal +
                '}';
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG, (byte) (intVal >>> 24), (byte) (intVal >>> 16), (byte) (intVal >>> 8), (byte) (intVal & 0xFF)};
    }
}

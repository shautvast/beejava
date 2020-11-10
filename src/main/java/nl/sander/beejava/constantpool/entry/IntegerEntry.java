package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class IntegerEntry extends LeafEntry {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerEntry that = (IntegerEntry) o;
        return intVal == that.intVal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intVal);
    }
}

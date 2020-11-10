package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public final class StringEntry extends ConstantPoolEntry {
    private static final byte TAG = 8;
    private final Utf8Entry utf8;

    public StringEntry(Utf8Entry utf8) {
        this.utf8 = utf8;
    }

    public int getUtf8Index() {
        return utf8.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getUtf8Index()), lowerByte(getUtf8Index())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringEntry that = (StringEntry) o;
        return utf8.equals(that.utf8);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utf8);
    }

    @Override
    public String toString() {
        return "StringEntry{" +
                "utf8Index=" + getUtf8Index() +
                '}';
    }
}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public final class StringEntry extends ConstantPoolEntry {
    private static final byte TAG = 8;
    private final Utf8Entry utf8Entry;

    public StringEntry(Utf8Entry utf8Entry) {
        super(utf8Entry);
        this.utf8Entry = utf8Entry;
    }

    public int getUtf8Index() {
        return utf8Entry.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getUtf8Index()), lowerByte(getUtf8Index())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringEntry that = (StringEntry) o;
        return utf8Entry.equals(that.utf8Entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utf8Entry);
    }

    @Override
    public String toString() {
        return "String\t\t#" + getUtf8Index() +"\t\t // "+ utf8Entry.getUtf8();
    }
}

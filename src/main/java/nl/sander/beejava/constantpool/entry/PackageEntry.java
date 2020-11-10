package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public final class PackageEntry extends ConstantPoolEntry {
    private static final byte TAG = 20;
    private final Utf8Entry name;

    public PackageEntry(Utf8Entry name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getNameIndex()), lowerByte(getNameIndex())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageEntry that = (PackageEntry) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PackageEntry{" +
                "nameIndex=" + getNameIndex() +
                '}';
    }
}

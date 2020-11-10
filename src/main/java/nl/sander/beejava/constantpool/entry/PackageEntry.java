package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public final class PackageEntry extends ConstantPoolEntry {
    private static final byte TAG = 20;
    private final Utf8Entry nameEntry;

    public PackageEntry(Utf8Entry nameEntry) {
        super(nameEntry);
        this.nameEntry = nameEntry;
    }

    public int getNameIndex() {
        return nameEntry.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getNameIndex()), lowerByte(getNameIndex())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageEntry that = (PackageEntry) o;
        return nameEntry.equals(that.nameEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameEntry);
    }

    @Override
    public String toString() {
        return "Package\t\t#" + getNameIndex() + "\t\t // " + nameEntry.getUtf8();
    }
}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class ModuleEntry extends ConstantPoolEntry {

    private static final byte TAG = 19;

    private final Utf8Entry nameEntry;

    public ModuleEntry(Utf8Entry nameEntry) {
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
        ModuleEntry that = (ModuleEntry) o;
        return nameEntry.equals(that.nameEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameEntry);
    }

    @Override
    public String toString() {
        return "Module\t\t#" + getNameIndex() +"\t\t // "+ nameEntry.getUtf8();
    }
}

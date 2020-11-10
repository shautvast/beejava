package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public final class NameAndTypeEntry extends ConstantPoolEntry {
    private static final byte TAG = 12;
    private final Utf8Entry name;
    private final Utf8Entry descriptor;

    public NameAndTypeEntry(Utf8Entry name, Utf8Entry descriptor) {
        super(name, descriptor);
        this.name = name;
        this.descriptor = descriptor;
    }


    public int getNameIndex() {
        return name.getIndex();
    }

    public int getDescriptorIndex() {
        return descriptor.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getNameIndex()), lowerByte(getNameIndex()), upperByte(getDescriptorIndex()), lowerByte(getDescriptorIndex())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameAndTypeEntry that = (NameAndTypeEntry) o;
        return name.equals(that.name) &&
                descriptor.equals(that.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, descriptor);
    }

    @Override
    public String toString() {
        return "NameAndTypeEntry{" +
                "nameIndex=" + getNameIndex() +
                ", typeIndex=" + getDescriptorIndex() +
                '}';
    }

}

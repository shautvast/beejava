package nl.sander.beejava.constantpool.entry;

public class NameAndTypeEntry extends ConstantPoolEntry {
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

    @Override
    public String toString() {
        return "NameAndTypeEntry{" +
                "nameIndex=" + getNameIndex() +
                ", typeIndex=" + getDescriptorIndex() +
                '}';
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getNameIndex()), lowerByte(getNameIndex()), upperByte(getDescriptorIndex()), lowerByte(getDescriptorIndex())};
    }
}

package nl.sander.beejava.constantpool.entry;

public class PackageEntry extends ConstantPoolEntry {
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
}

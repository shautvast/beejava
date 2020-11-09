package nl.sander.beejava.constantpool.entry;

public class ClassEntry extends NodeConstant {
    private static final byte TAG = 7;
    private final Utf8Entry name;

    public ClassEntry(Utf8Entry name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }

    @Override
    public String toString() {
        return "ClassEntry{" +
                "nameIndex=" + getNameIndex() +
                '}';
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getNameIndex()), lowerByte(getNameIndex())};
    }
}

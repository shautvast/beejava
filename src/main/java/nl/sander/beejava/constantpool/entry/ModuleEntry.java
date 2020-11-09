package nl.sander.beejava.constantpool.entry;

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
}

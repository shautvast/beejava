package nl.sander.beejava.constantpool.entry;

public class ConstantModule extends NodeConstant {

    private static final byte TAG = 19;

    private final ConstantUtf8 nameEntry;

    public ConstantModule(ConstantUtf8 nameEntry) {
        super(nameEntry);
        this.nameEntry = nameEntry;
    }

    public int getNameIndex() {
        return nameEntry.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperFraction(getNameIndex()), lowerFraction(getNameIndex())};
    }
}

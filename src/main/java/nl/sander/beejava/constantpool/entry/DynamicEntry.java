package nl.sander.beejava.constantpool.entry;

public class DynamicEntry extends NodeConstant {
    private static final byte TAG = 17;
    private final int bootstrapMethodIndex; // TODO
    private final NameAndTypeEntry nameAndType;

    public DynamicEntry(int bootstrapMethodIndex, NameAndTypeEntry nameAndType) {
        this.bootstrapMethodIndex = bootstrapMethodIndex;
        this.nameAndType = nameAndType;
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG};
    }

    public int getNameAndTypeIndex() {
        return nameAndType.getIndex();
    }
}

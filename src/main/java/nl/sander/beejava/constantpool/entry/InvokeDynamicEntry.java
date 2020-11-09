package nl.sander.beejava.constantpool.entry;

public class InvokeDynamicEntry extends NodeConstant {
    private static final byte TAG = 18;

    private final int bootstrapMethodAttrIndex; //??
    private final NameAndTypeEntry nameAndTypeEntry;

    public InvokeDynamicEntry(int bootstrapMethodAttrIndex, NameAndTypeEntry nameAndTypeEntry) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeEntry = nameAndTypeEntry;
    }


    @Override
    public String toString() {
        return "InvokeDynamicEntry{" +
                "bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
                ", nameAndTypeIndex=" + nameAndTypeEntry.getIndex() +
                '}';
    }


    public byte[] getBytes() {
        return new byte[]{TAG};
    }
}
//TODO implement later
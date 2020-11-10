package nl.sander.beejava.constantpool.entry;

public class InvokeDynamicEntry extends ConstantPoolEntry {
    private static final byte TAG = 18;

    private final int bootstrapMethodAttrIndex; //??
    private final NameAndTypeEntry nameAndTypeEntry;

    public InvokeDynamicEntry(int bootstrapMethodAttrIndex, NameAndTypeEntry nameAndTypeEntry) {
        super( nameAndTypeEntry); //TODO /* bootstrapMethodAttrIndex */,
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeEntry = nameAndTypeEntry;
    }


    @Override
    public String toString() {
        return "InvokeDynamic\tbootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
                ", nameAndTypeIndex=" + nameAndTypeEntry.getIndex();
    }


    public byte[] getBytes() {
        return new byte[]{TAG};
    }

    // TODO equals and hashcode
}
//TODO implement later
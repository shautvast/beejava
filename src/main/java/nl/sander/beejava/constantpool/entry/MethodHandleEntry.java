package nl.sander.beejava.constantpool.entry;

//TODO implement later
public class MethodHandleEntry extends ConstantPoolEntry {
    private static final byte TAG = 15;

    private final int referenceKind;

    // only 1 of these can be present:
    private FieldRefEntry fieldRefEntry;
    private MethodRefEntry methodRefEntry;
    private InterfaceMethodRefEntry interfaceMethodRefEntry;

    public MethodHandleEntry(int referenceKind) {
        this.referenceKind = referenceKind;
    }

    @Override
    public String toString() {
        return "MethodHandleEntry{" +
                "referenceKind=" + referenceKind +
                ", referenceIndex=" + getReferenceIndex() +
                '}';
    }

    private int getReferenceIndex() {
        return 0; //TODO implement
    }

    public byte[] getBytes() {
        return new byte[]{TAG};
    }

    //TODO equals and hashcode
}
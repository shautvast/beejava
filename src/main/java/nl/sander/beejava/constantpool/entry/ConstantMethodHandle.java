package nl.sander.beejava.constantpool.entry;

//TODO implement later
public class ConstantMethodHandle extends NodeConstant {
    private final int referenceKind;

    // only 1 of these can be present:
    private ConstantFieldRef constantFieldRef;
    private ConstantMethodRef constantMethodRef;
    private ConstantInterfaceMethodRef constantInterfaceMethodRef;

    public ConstantMethodHandle(int referenceKind) {
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

    @Override
    public int getTag() {
        return 15;
    }
}
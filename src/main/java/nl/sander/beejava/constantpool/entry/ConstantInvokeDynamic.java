package nl.sander.beejava.constantpool.entry;

public class ConstantInvokeDynamic extends NodeConstant {
    private static final byte TAG = 18;

    private final int bootstrapMethodAttrIndex; //??
    private final ConstantNameAndType constantNameAndType;

    public ConstantInvokeDynamic(int bootstrapMethodAttrIndex, ConstantNameAndType constantNameAndType) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.constantNameAndType = constantNameAndType;
    }


    @Override
    public String toString() {
        return "InvokeDynamicEntry{" +
                "bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
                ", nameAndTypeIndex=" + constantNameAndType.getIndex() +
                '}';
    }


    public byte[] getBytes() {
        return new byte[]{TAG};
    }
}
//TODO implement later
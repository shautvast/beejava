package nl.sander.beejava.constantpool.entry;

public class ConstantInvokeDynamic extends NodeConstant {
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

    @Override
    public int getTag() {
        return 18;
    }
}
//TODO implement later
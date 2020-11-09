package nl.sander.beejava.constantpool.entry;

public class ConstantMethodRef extends NodeConstant {
    private static final byte TAG = 10;

    private final ConstantClass constantClass;
    private final ConstantNameAndType constantNameAndType;

    public ConstantMethodRef(ConstantClass constantClass, ConstantNameAndType constantNameAndType) {
        super(constantClass, constantNameAndType);
        this.constantClass = constantClass;
        this.constantNameAndType = constantNameAndType;
    }

    public int getClassIndex() {
        return constantClass.getIndex();
    }

    public int getNameAndTypeIndex() {
        return constantNameAndType.getIndex();
    }


    public byte[] getBytes() {
        return new byte[]{TAG};
    }
}

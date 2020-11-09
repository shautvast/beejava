package nl.sander.beejava.constantpool.entry;

public class ConstantInterfaceMethodRef extends NodeConstant {
    private final ConstantClass constantClass;
    private final ConstantNameAndType constantNameAndType;

    public ConstantInterfaceMethodRef(ConstantClass constantClass, ConstantNameAndType constantNameAndType) {
        super(constantClass, constantNameAndType);
        this.constantClass = constantClass;
        this.constantNameAndType = constantNameAndType;
    }

    public int getClassIndex(){
        return constantClass.getIndex();
    }

    public int getNameAndTypeIndex(){
        return constantNameAndType.getIndex();
    }

    @Override
    public int getTag() {
        return 11;
    }
}

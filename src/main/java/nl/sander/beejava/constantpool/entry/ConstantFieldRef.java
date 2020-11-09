package nl.sander.beejava.constantpool.entry;

public class ConstantFieldRef extends NodeConstant {
    private final ConstantClass constantClass;
    private final ConstantNameAndType constantNameAndType;

    public ConstantFieldRef(ConstantClass constantClass, ConstantNameAndType constantNameAndType) {
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

    @Override
    public String toString() {
        return "FieldRefEntry{" +
                "classIndex=" + getClassIndex() +
                ", nameAndTypeIndex=" + getNameAndTypeIndex() +
                '}';
    }

    @Override
    public int getTag() {
        return 9;
    }
}

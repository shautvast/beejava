package nl.sander.beejava.constantpool.entry;

public class ConstantDynamic extends NodeConstant {
    private static final byte TAG = 17;
    private final int bootstrapMethodIndex; // TODO
    private final ConstantNameAndType nameAndType;

    public ConstantDynamic(int bootstrapMethodIndex, ConstantNameAndType nameAndType) {
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

package nl.sander.beejava.constantpool.entry;

public class ConstantDynamic extends NodeConstant {
    private final int bootstrapMethodIndex; // TODO
    private final ConstantNameAndType nameAndType;

    public ConstantDynamic(int bootstrapMethodIndex, ConstantNameAndType nameAndType) {
        this.bootstrapMethodIndex = bootstrapMethodIndex;
        this.nameAndType = nameAndType;
    }

    @Override
    public int getTag() {
        return 17;
    }

    public int getNameAndTypeIndex(){
        return nameAndType.getIndex();
    }
}

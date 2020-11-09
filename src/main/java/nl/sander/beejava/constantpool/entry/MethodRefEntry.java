package nl.sander.beejava.constantpool.entry;

public class MethodRefEntry extends NodeConstant {
    private static final byte TAG = 10;

    private final ClassEntry classRef;
    private final NameAndTypeEntry nameAndType;

    public MethodRefEntry(ClassEntry classRef, NameAndTypeEntry nameAndType) {
        super(classRef, nameAndType);
        this.classRef = classRef;
        this.nameAndType = nameAndType;
    }

    public int getClassIndex() {
        return classRef.getIndex();
    }

    public int getNameAndTypeIndex() {
        return nameAndType.getIndex();
    }


    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getClassIndex()), lowerByte(getClassIndex()), upperByte(getNameAndTypeIndex()), lowerByte(getNameAndTypeIndex())};
    }
}

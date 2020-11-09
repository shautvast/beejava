package nl.sander.beejava.constantpool.entry;

public class FieldRefEntry extends ConstantPoolEntry {
    private static final byte TAG = 9;

    private final ClassEntry classEntry;
    private final NameAndTypeEntry nameAndTypeEntry;

    public FieldRefEntry(ClassEntry classEntry, NameAndTypeEntry nameAndTypeEntry) {
        super(classEntry, nameAndTypeEntry);
        this.classEntry = classEntry;
        this.nameAndTypeEntry = nameAndTypeEntry;
    }

    public int getClassIndex() {
        return classEntry.getIndex();
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeEntry.getIndex();
    }

    @Override
    public String toString() {
        return "FieldRefEntry{" +
                "classIndex=" + getClassIndex() +
                ", nameAndTypeIndex=" + getNameAndTypeIndex() +
                '}';
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG};
    }

}

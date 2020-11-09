package nl.sander.beejava.constantpool.entry;

public class InterfaceMethodRefEntry extends ConstantPoolEntry {
    private static final byte TAG = 11;

    private final ClassEntry classEntry;
    private final NameAndTypeEntry nameAndTypeEntry;

    public InterfaceMethodRefEntry(ClassEntry classEntry, NameAndTypeEntry nameAndTypeEntry) {
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
    public byte[] getBytes() {
        return new byte[]{TAG};
    }
}

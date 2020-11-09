package nl.sander.beejava.constantpool.entry;

public class InterfaceMethodRefEntry extends ConstantPoolEntry {
    private final ClassEntry classEntry;
    private final NameAndTypeEntry nameAndTypeEntry;

    public InterfaceMethodRefEntry(ClassEntry classEntry, NameAndTypeEntry nameAndTypeEntry) {
        super(classEntry,nameAndTypeEntry);
        this.classEntry = classEntry;
        this.nameAndTypeEntry = nameAndTypeEntry;
    }

    public int getClassIndex(){
        return classEntry.getIndex();
    }

    public int getNameAndTypeIndex(){
        return nameAndTypeEntry.getIndex();
    }
}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterfaceMethodRefEntry that = (InterfaceMethodRefEntry) o;
        return classEntry.equals(that.classEntry) &&
                nameAndTypeEntry.equals(that.nameAndTypeEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classEntry, nameAndTypeEntry);
    }
}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldRefEntry that = (FieldRefEntry) o;
        return classEntry.equals(that.classEntry) &&
                nameAndTypeEntry.equals(that.nameAndTypeEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classEntry, nameAndTypeEntry);
    }
}

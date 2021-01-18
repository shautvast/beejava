package nl.sander.bejava.constantpool.entry;

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
        return "FieldRef\t\t#" + getClassIndex() +
                ".#=" + getNameAndTypeIndex() + "\t// " + classEntry.getName() + '.' + nameAndTypeEntry.getName() + ':' + nameAndTypeEntry.getType();

    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getClassIndex()), lowerByte(getClassIndex()), upperByte(getNameAndTypeIndex()),lowerByte(getNameAndTypeIndex())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (FieldRefEntry) o;
        return classEntry.equals(that.classEntry) &&
                nameAndTypeEntry.equals(that.nameAndTypeEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classEntry, nameAndTypeEntry);
    }
}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class MethodRefEntry extends ConstantPoolEntry {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodRefEntry that = (MethodRefEntry) o;
        return classRef.equals(that.classRef) &&
                nameAndType.equals(that.nameAndType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classRef, nameAndType);
    }

    @Override
    public String toString() {
        return "MethodRefEntry{" +
                "classIndex=" + getClassIndex() +
                ", nameAndTypeIndex=" + getNameAndTypeIndex() +
                '}';
    }


}

package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class DynamicEntry extends ConstantPoolEntry {
    private static final byte TAG = 17;
    private final int bootstrapMethodIndex; // TODO
    private final NameAndTypeEntry nameAndType;

    public DynamicEntry(int bootstrapMethodIndex, NameAndTypeEntry nameAndType) {
        super(nameAndType); // TODO
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        DynamicEntry that = (DynamicEntry) o;
//        return bootstrapMethodIndex == that.bootstrapMethodIndex &&
//                nameAndType.equals(that.nameAndType);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(bootstrapMethodIndex, nameAndType);
//    }

    @Override
    public String toString() {
        return "DynamicEntry{" +
                "bootstrapMethodIndex=" + bootstrapMethodIndex +
                ", nameAndType=" + nameAndType +
                '}';
    }
}

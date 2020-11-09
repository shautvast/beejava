package nl.sander.beejava.constantpool.entry;

public class ConstantNameAndType extends NodeConstant {
    private static final byte TAG = 12;
    private final ConstantUtf8 name;
    private final ConstantUtf8 descriptor;

    public ConstantNameAndType(ConstantUtf8 name, ConstantUtf8 descriptor) {
        super(name, descriptor);
        this.name = name;
        this.descriptor = descriptor;
    }


    public int getNameIndex() {
        return name.getIndex();
    }

    public int getDescriptorIndex() {
        return descriptor.getIndex();
    }

    @Override
    public String toString() {
        return "NameAndTypeEntry{" +
                "nameIndex=" + getNameIndex() +
                ", typeIndex=" + getDescriptorIndex() +
                '}';
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperFraction(getNameIndex()), lowerFraction(getNameIndex()), upperFraction(getDescriptorIndex()), lowerFraction(getDescriptorIndex())};
    }
}

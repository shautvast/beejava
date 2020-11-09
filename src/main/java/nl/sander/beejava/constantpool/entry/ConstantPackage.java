package nl.sander.beejava.constantpool.entry;

public class ConstantPackage extends NodeConstant {
    private static final byte TAG = 20;
    private final ConstantUtf8 name;

    public ConstantPackage(ConstantUtf8 name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }

    public byte[] getBytes() {
        return new byte[]{TAG, upperFraction(getNameIndex()), lowerFraction(getNameIndex())};
    }
}

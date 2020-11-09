package nl.sander.beejava.constantpool.entry;

public class ConstantPackage extends NodeConstant {
    private final ConstantUtf8 name;

    public ConstantPackage(ConstantUtf8 name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }

    @Override
    public int getTag() {
        return 20;
    }
}

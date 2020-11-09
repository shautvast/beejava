package nl.sander.beejava.constantpool.entry;

public class ConstantClass extends NodeConstant {
    private final ConstantUtf8 name;

    public ConstantClass(ConstantUtf8 name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }

    @Override
    public String toString() {
        return "ClassEntry{" +
                "nameIndex=" + getNameIndex() +
                '}';
    }

    @Override
    public int getTag() {
        return 7;
    }
}

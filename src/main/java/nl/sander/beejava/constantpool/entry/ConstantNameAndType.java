package nl.sander.beejava.constantpool.entry;

public class ConstantNameAndType extends NodeConstant {
    private final ConstantUtf8 name;
    private final ConstantUtf8 type;

    public ConstantNameAndType(ConstantUtf8 name, ConstantUtf8 type) {
        super(name,type);
        this.name = name;
        this.type = type;
    }


    public int getNameIndex() {
        return name.getIndex();
    }

    public int getTypeIndex() {
        return type.getIndex();
    }

    @Override
    public String toString() {
        return "NameAndTypeEntry{" +
                "nameIndex=" + getNameIndex() +
                ", typeIndex=" + getTypeIndex() +
                '}';
    }

    @Override
    public int getTag() {
        return 12;
    }
}

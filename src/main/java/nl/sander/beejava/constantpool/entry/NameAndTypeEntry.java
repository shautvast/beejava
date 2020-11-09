package nl.sander.beejava.constantpool.entry;

public class NameAndTypeEntry extends ConstantPoolEntry {
    private final Utf8Entry name;
    private final Utf8Entry type;

    public NameAndTypeEntry(Utf8Entry name, Utf8Entry type) {
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
}

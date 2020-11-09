package nl.sander.beejava.constantpool.entry;

public class PackageEntry extends ConstantPoolEntry {
    private final Utf8Entry name;

    public PackageEntry(Utf8Entry name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }
}

package nl.sander.beejava.constantpool.entry;

public class ModuleEntry extends ConstantPoolEntry {

    private final Utf8Entry nameEntry;

    public ModuleEntry(Utf8Entry nameEntry) {
        super(nameEntry);
        this.nameEntry = nameEntry;
    }
}

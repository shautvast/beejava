package nl.sander.beejava.constantpool.entry;

public class ConstantModule extends NodeConstant {

    private final ConstantUtf8 nameEntry;

    public ConstantModule(ConstantUtf8 nameEntry) {
        super(nameEntry);
        this.nameEntry = nameEntry;
    }

    @Override
    public int getTag() {
        return 19;
    }
}

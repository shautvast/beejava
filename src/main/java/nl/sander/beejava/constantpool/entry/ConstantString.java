package nl.sander.beejava.constantpool.entry;

public class ConstantString extends NodeConstant {
    private final ConstantUtf8 utf8;

    public ConstantString(ConstantUtf8 utf8) {
        this.utf8 = utf8;
    }

    public int getUtf8Index() {
        return utf8.getIndex();
    }

    @Override
    public String toString() {
        return "StringEntry{" +
                "utf8Index=" + getUtf8Index() +
                '}';
    }

    @Override
    public int getTag() {
        return 8;
    }
}

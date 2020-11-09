package nl.sander.beejava.constantpool.entry;

public class StringEntry extends ConstantPoolEntry {
    private final Utf8Entry utf8;

    public StringEntry(Utf8Entry utf8) {
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
}

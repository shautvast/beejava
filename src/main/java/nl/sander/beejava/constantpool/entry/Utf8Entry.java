package nl.sander.beejava.constantpool.entry;

public class Utf8Entry extends LeafEntry {
    private final String stringVal;

    public Utf8Entry(String utf8) {
        this.stringVal = utf8;
    }

    public String getUtf8() {
        return stringVal;
    }

    @Override
    public String toString() {
        return "Utf8Entry{" +
                "stringVal='" + stringVal + '\'' +
                '}';
    }
}

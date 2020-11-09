package nl.sander.beejava.constantpool.entry;

public class LongEntry extends LeafEntry {

    private final long longVal;

    public LongEntry(long longVal) {
        this.longVal = longVal;
    }

    @Override
    public String toString() {
        return "LongEntry{" +
                "longVal=" + longVal +
                '}';
    }
}

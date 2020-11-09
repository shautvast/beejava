package nl.sander.beejava.constantpool.entry;

public class FloatEntry extends LeafEntry {
    private final float floatVal;

    public FloatEntry(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public String toString() {
        return "FloatEntry{" +
                "floatVal=" + floatVal +
                '}';
    }
}

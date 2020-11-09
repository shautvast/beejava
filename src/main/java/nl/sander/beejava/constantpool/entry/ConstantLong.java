package nl.sander.beejava.constantpool.entry;

public class ConstantLong extends LeafConstant {

    private final long longVal;

    public ConstantLong(long longVal) {
        this.longVal = longVal;
    }

    @Override
    public String toString() {
        return "LongEntry{" +
                "longVal=" + longVal +
                '}';
    }

    @Override
    public int getTag() {
        return 5;
    }
}

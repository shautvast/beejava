package nl.sander.beejava.constantpool.entry;

public class ConstantFloat extends LeafConstant {
    private final float floatVal;

    public ConstantFloat(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public String toString() {
        return "FloatEntry{" +
                "floatVal=" + floatVal +
                '}';
    }

    @Override
    public int getTag() {
        return 4;
    }
}

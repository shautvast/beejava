package nl.sander.beejava.constantpool.entry;

public class ConstantDouble extends LeafConstant {
    private final double doubleVal;

    public ConstantDouble(double doubleVal) {
        this.doubleVal = doubleVal;
    }

    @Override
    public int getTag() {
        return 6;
    }
}

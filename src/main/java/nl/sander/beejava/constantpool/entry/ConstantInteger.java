package nl.sander.beejava.constantpool.entry;

public class ConstantInteger extends LeafConstant {
    private final int intVal;

    public ConstantInteger(int integer) {
        this.intVal = integer;
    }

    @Override
    public String toString() {
        return "IntEntry{" +
                "intVal=" + intVal +
                '}';
    }

    @Override
    public int getTag() {
        return 3;
    }
}

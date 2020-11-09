package nl.sander.beejava.constantpool.entry;

public class IntEntry extends LeafEntry {
    private final int intVal;

    public IntEntry(int integer) {
        this.intVal = integer;
    }

    @Override
    public String toString() {
        return "IntEntry{" +
                "intVal=" + intVal +
                '}';
    }
}

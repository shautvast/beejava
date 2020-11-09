package nl.sander.beejava.constantpool.entry;

public class ConstantUtf8 extends LeafConstant {
    private final String value;

    public ConstantUtf8(String utf8) {
        this.value = utf8;
    }

    public String getUtf8() {
        return value;
    }

    @Override
    public String toString() {
        return "Utf8Entry{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public int getTag() {
        return 1;
    }
}

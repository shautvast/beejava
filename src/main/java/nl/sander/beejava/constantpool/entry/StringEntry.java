package nl.sander.beejava.constantpool.entry;

public class StringEntry extends ConstantPoolEntry {
    private static final byte TAG = 8;
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

    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getUtf8Index()), lowerByte(getUtf8Index())};
    }
}

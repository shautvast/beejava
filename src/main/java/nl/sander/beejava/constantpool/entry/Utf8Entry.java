package nl.sander.beejava.constantpool.entry;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class Utf8Entry extends LeafEntry {
    private static final byte TAG = 1;
    private final String value;

    public Utf8Entry(String utf8) {
        this.value = utf8;
    }

    public String getUtf8() {
        return value;
    }

    public byte[] getBytes() {
        byte[] utf8Bytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = new byte[utf8Bytes.length + 3];
        bytes[0] = TAG;
        bytes[1] = upperByte(utf8Bytes.length);
        bytes[2] = lowerByte(utf8Bytes.length);
        System.arraycopy(utf8Bytes, 0, bytes, 3, utf8Bytes.length);
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utf8Entry utf8Entry = (Utf8Entry) o;
        return value.equals(utf8Entry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Utf8Entry{" +
                "value='" + value + '\'' +
                '}';
    }
}

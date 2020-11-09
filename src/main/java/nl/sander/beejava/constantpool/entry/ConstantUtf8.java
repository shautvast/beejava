package nl.sander.beejava.constantpool.entry;

import java.nio.charset.StandardCharsets;

public class ConstantUtf8 extends LeafConstant {
    private static final byte TAG = 1;
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

    public byte[] getBytes() {
        byte[] utf8Bytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = new byte[utf8Bytes.length + 3];
        bytes[0] = TAG;
        bytes[1] = upperFraction(bytes.length);
        bytes[2] = lowerFraction(bytes.length);
        System.arraycopy(utf8Bytes, 0, bytes, 3, utf8Bytes.length);
        return bytes;
    }
}

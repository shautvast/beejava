package nl.sander.beejava.constantpool.entry;

public class MethodTypeEntry extends ConstantPoolEntry {
    private static final byte TAG = 16;

    private final Utf8Entry methodDescriptor;

    public MethodTypeEntry(Utf8Entry methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    @Override
    public String toString() {
        return "MethodTypeEntry{" +
                "methodDescriptor=" + methodDescriptor +
                '}';
    }


    public byte[] getBytes() {
        return new byte[]{TAG};
    }
}

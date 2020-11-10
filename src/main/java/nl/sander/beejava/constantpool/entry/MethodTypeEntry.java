package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class MethodTypeEntry extends ConstantPoolEntry {
    private static final byte TAG = 16;

    private final Utf8Entry methodDescriptor;

    public MethodTypeEntry(Utf8Entry methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    public byte[] getBytes() {
        return new byte[]{TAG};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodTypeEntry that = (MethodTypeEntry) o;
        return methodDescriptor.equals(that.methodDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodDescriptor);
    }

    @Override
    public String toString() {
        return "MethodTypeEntry{" +
                "methodDescriptor=" + methodDescriptor +
                '}';
    }
}

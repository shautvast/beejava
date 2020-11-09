package nl.sander.beejava.constantpool.entry;

public class ConstantMethodType extends NodeConstant {
    private static final byte TAG = 16;

    private final ConstantUtf8 methodDescriptor;

    public ConstantMethodType(ConstantUtf8 methodDescriptor) {
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

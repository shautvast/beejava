package nl.sander.beejava.constantpool.entry;

public class ConstantMethodType extends NodeConstant {
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

    @Override
    public int getTag() {
        return 16;
    }
}

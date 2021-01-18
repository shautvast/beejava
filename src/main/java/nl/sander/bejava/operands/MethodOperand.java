package nl.sander.bejava.operands;

/**
 * The operand is a method call.
 */
public class MethodOperand extends Operand{
    public final String className;
    public final String methodName;
    public final String signature;

    public MethodOperand(String className, String methodName, String signature) {
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "MethodOperand{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}

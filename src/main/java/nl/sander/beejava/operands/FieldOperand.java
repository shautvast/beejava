package nl.sander.beejava.operands;

public class FieldOperand extends Operand{
    public final String className;
    public final String fieldName;

    public FieldOperand(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }
}

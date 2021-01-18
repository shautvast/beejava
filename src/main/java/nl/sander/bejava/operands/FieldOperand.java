package nl.sander.bejava.operands;

/**
 * The operand is a field member of the class
 */
public class FieldOperand extends Operand{
    public final String className;
    public final String fieldName;

    public FieldOperand(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }
}

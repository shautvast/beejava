package nl.sander.bejava.operands;

/**
 * The operand is a local variable
 */
public class LocalVariableOperand extends Operand{
    public final String name;

    public LocalVariableOperand(String name) {
        this.name = name;
    }
}

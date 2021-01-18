package nl.sander.beejava.operands;

public class LocalVariableOperand extends Operand{
    public final String name;

    public LocalVariableOperand(String name) {
        this.name = name;
    }
}

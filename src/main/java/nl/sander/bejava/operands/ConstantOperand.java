package nl.sander.bejava.operands;

import nl.sander.bejava.Primitive;

/**
 * The operand is a constant
 */
public class ConstantOperand extends Operand {
    private final Primitive type;
    private final String value;


    public ConstantOperand(Primitive type, String value) {
        this.type = type;
        this.value = value;
    }

    public Primitive getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}

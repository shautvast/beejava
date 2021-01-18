package nl.sander.beejava.operands;

import nl.sander.beejava.Primitive;

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

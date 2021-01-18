package nl.sander.bejava.operands;

/**
 * The operand is void. An operand is compulsory in a {@link nl.sander.bejava.JavaInstruction}.
 * Used when the opcode is RETURN and the return type is void.
 */
public class VoidOperand extends Operand{
    public final static VoidOperand INSTANCE = new VoidOperand();

    private VoidOperand() {
        //
    }
}

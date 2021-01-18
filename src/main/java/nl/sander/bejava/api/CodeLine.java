package nl.sander.bejava.api;


public class CodeLine extends Instruction {
    private final Opcode opcode;
    private CodeContainer owner;

    public CodeLine(Opcode opcode, String operand) {
        super(operand);
        this.opcode = opcode;
    }

    public String getOperand() {
        return operand;
    }

    public Opcode getOpcode() {
        return opcode;
    }
}

package nl.sander.beejava.api;


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

    public void setOwner(CodeContainer owner) {
        this.owner = owner;
    }

    public CodeContainer getOwner() {
        return owner;
    }
}

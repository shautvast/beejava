package nl.sander.bejava.api;

public class ClassInstruction extends Instruction {
    private final ClassOperation classOperation;

    public ClassInstruction(ClassOperation classOperation, String operand) {
        super(operand);
        this.classOperation = classOperation;
    }

    public String getOperand() {
        return operand;
    }

    public ClassOperation getOperation() {
        return classOperation;
    }
}

package nl.sander.beejava.api;


public class CodeLine {
    private final int linenumber;
    private final Opcode opcode;
    private Ref ref;
    private BeeParameter parameter;
    private String methodName;
    private String inputSignature;
    private String outputSignature;
    private BeeField field;

    CodeLine(int linenumber, Opcode opcode) {
        this.linenumber = linenumber;
        this.opcode = opcode;
    }

    public static CodeLine line(int nr, Opcode opcode, Ref ref) {
        return new CodeLine(nr, opcode).withRef(ref);
    }

    public static CodeLine line(int nr, Opcode opcode, Ref ref, String methodNameRef, String inputSignature) {
        return new CodeLine(nr, opcode).withRef(ref).withMethodName(methodNameRef).withInput(inputSignature).withVoidOutput();
    }

    public static CodeLine line(int nr, Opcode opcode, Ref ref, String methodNameRef, String inputSignature, String outputSignature) {
        return new CodeLine(nr, opcode).withRef(ref).withMethodName(methodNameRef).withInput(inputSignature).withOutput(outputSignature);
    }

    public static CodeLine line(int nr, Opcode opcode) {
        return new CodeLine(nr, opcode);
    }

    public static CodeLine line(int nr, Opcode opcode, BeeParameter parameter) {
        return new CodeLine(nr, opcode).withParameter(parameter);
    }

    public static CodeLine line(int nr, Opcode opcode, BeeField intField) {
        return new CodeLine(nr, opcode).withRef(Ref.THIS).withField(intField);
    }

    private CodeLine withRef(Ref ref) {
        this.ref = ref;
        return this;
    }

    private CodeLine withMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    private CodeLine withInput(String inputSignature) {
        this.inputSignature = inputSignature;
        return this;
    }

    private CodeLine withVoidOutput() {
        return withOutput("V");
    }

    private CodeLine withOutput(String outputSignature) {
        this.outputSignature = outputSignature;
        return this;
    }

    private CodeLine withParameter(BeeParameter parameter) {
        this.parameter = parameter;
        return this;
    }

    private CodeLine withField(BeeField field) {
        this.field = field;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean hasMethod() {
        return methodName != null;
    }

    public String getMethodSignature() {
        return inputSignature + outputSignature;
    }

    public Ref getRef() {
        return ref;
    }

    public boolean hasField() {
        return field != null;
    }

    public BeeField getField() {
        return field;
    }

    public BeeParameter getParameter() {
        return parameter;
    }


}

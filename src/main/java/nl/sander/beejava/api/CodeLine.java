package nl.sander.beejava.api;


public class CodeLine {
    private final int linenumber;
    private final Opcode opcode;
    private Ref ref;
    private BeeParameter parameter;
    private String className;
    private String methodName;
    private String inputSignature;
    private String outputSignature;
    private BeeField ownfield; // when you create a class with a field, you can refer to it
    private String externalfield; // when you refer to a field from another class

    private Object constValue;
    private String externalFieldType;

    CodeLine(int linenumber, Opcode opcode) {
        this.linenumber = linenumber;
        this.opcode = opcode;
    }

    public static CodeLine line(int nr, Opcode opcode, Ref ref) {
        return new CodeLine(nr, opcode).withRef(ref);
    }

    public static CodeLine line(int nr, Opcode opcode, String fieldClass, String fieldName) {
        return new CodeLine(nr, opcode).withExternalFieldRef(fieldClass, fieldName);
    }

    public static CodeLine line(int nr, Opcode opcode, Object constValue) {
        return new CodeLine(nr, opcode).withConstValue(constValue);
    }

    public static CodeLine line(int nr, Opcode opcode, String className, String methodName, String inputSignature) {
        return new CodeLine(nr, opcode).withClassName(className).withMethodName(methodName).withInput(inputSignature).withVoidOutput();
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
        return new CodeLine(nr, opcode).withRef(Ref.THIS).withOwnField(intField);
    }

    private CodeLine withRef(Ref ref) {
        this.ref = ref;
        return this;
    }

    private CodeLine withClassName(String className) {
        this.className = className;
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

    private CodeLine withConstValue(Object constValue) {
        this.constValue = constValue;
        return this; //TODO
    }

    private CodeLine withOwnField(BeeField beeField) {
        this.ownfield = beeField;
        return this;
    }

    private CodeLine withExternalFieldRef(String className, String field) {
        this.className = className;
        this.externalFieldType = getType(className, field);
        this.externalfield = field;
        return this;

    }

    // TODO decide whether to work with Strings or class objects...

    /*
     * Look up the type of a field in a class
     *
     * Assumes field is accessible
     *
     * @param className the class containing a field
     * @param field name of the field
     * @return the  field type
     */
    private String getType(String className, String field) {
        try {
            return Class.forName(className).getField(field).getType().getName();
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasClassName() {
        return className != null;
    }


    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean hasMethodCall() {
        return methodName != null;
    }

    public String getMethodSignature() {
        return inputSignature + outputSignature;
    }

    public Ref getRef() {
        return ref;
    }

    public boolean hasRef() {
        return ref != null;
    }

    public boolean hasRefToOwnField() {
        return ownfield != null;
    }


    public Object getConstValue() {
        return constValue;
    }

    public boolean hasConstValue() {
        return constValue != null;
    }

    public BeeField getOwnfield() {
        return ownfield;
    }

    public BeeParameter getParameter() {
        return parameter;
    }


    public boolean hasRefToExternalField() {
        return externalfield != null;
    }

    public String getExternalfield() {
        return externalfield;
    }

    public String getExternalfieldClass() {
        return externalFieldType;
    }
}

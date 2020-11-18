package nl.sander.beejava.api;


import nl.sander.beejava.CodeContainer;
import nl.sander.beejava.JavaOpcode;
import nl.sander.beejava.TypeMapper;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class CodeLine {
    private final Opcode opcode;
    private Ref ref;
    private BeeParameter parameter;
    private Class<?> type;
    private String methodName;
    private List<Class<?>> inputSignature;
    private String outputSignature;
    private BeeField ownfield; // when you create a class with a field, you can refer to it
    private Field externalfield; // when you refer to a field from another class
    private Object constValue;
    private ConstantPoolEntry assignedEntry;
    private CodeContainer owner;
    private JavaOpcode javaOpcode;

    CodeLine(Opcode opcode) {
        this.opcode = opcode;
    }

    public static CodeLine line(Opcode opcode, Ref ref) {
        return new CodeLine(opcode).withRef(ref);
    }

    public static CodeLine line(Opcode opcode, String fieldClass, String fieldName) {
        return new CodeLine(opcode).withExternalFieldRef(fieldClass, fieldName);
    }

    public static CodeLine line(Opcode opcode, Object constValue) {
        return new CodeLine(opcode).withConstValue(constValue);
    }

    public static CodeLine line(Opcode opcode, String className, String methodName, String inputSignature) throws ClassNotFoundException {
        return new CodeLine(opcode).withClassName(className).withMethodName(methodName).withInput(parse(inputSignature)).withVoidOutput();
    }

    public static CodeLine line(Opcode opcode,
                                Ref ref, String methodNameRef, String inputSignature) throws ClassNotFoundException {
        return new CodeLine(opcode).withRef(ref).withMethodName(methodNameRef).withInput(parse(inputSignature)).withVoidOutput();
    }

    public static CodeLine line(Opcode opcode,
                                Ref ref, String methodNameRef, String inputSignature, String outputSignature) throws ClassNotFoundException {
        return new CodeLine(opcode).withRef(ref).withMethodName(methodNameRef).withInput(parse(inputSignature)).withOutput(outputSignature);
    }

    public static CodeLine line(Opcode opcode) {
        return new CodeLine(opcode);
    }

    public static CodeLine line(Opcode opcode, BeeParameter parameter) {
        return new CodeLine(opcode).withParameter(parameter);
    }

    public static CodeLine line(Opcode opcode, BeeField intField) {
        return new CodeLine(opcode).withRef(Ref.THIS).withOwnField(intField);
    }

    private CodeLine withRef(Ref ref) {
        this.ref = ref;
        return this;
    }

    private CodeLine withClassName(String className) {
        this.type = loadClass(className);
        return this;
    }

    private CodeLine withMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    private CodeLine withInput(List<Class<?>> inputSignature) {
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
        this.type = loadClass(className);
        this.externalfield = loadField(field);
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
    private Field loadField(String field) {
        try {
            return type.getField(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasClassName() {
        return type != null;
    }


    public String getClassName() {
        return type.getName();
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean hasMethodCall() {
        return methodName != null;
    }

    public String getMethodSignature() {
        return inputSignature.stream()
                .map(TypeMapper::map)
                .collect(Collectors.joining(",", "(", ")")) + outputSignature;
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

    public Field getExternalfield() {
        return externalfield;
    }

    public ConstantPoolEntry getAssignedEntry() {
        return assignedEntry;
    }

    public void setAssignedEntry(ConstantPoolEntry assignedEntry) {
        this.assignedEntry = assignedEntry;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    private Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //TODO specific exception
        }
    }

    public CodeContainer getOwner() {
        return owner;
    }

    public void setOwner(CodeContainer codeContainer) {
        this.owner = codeContainer;
    }

    public JavaOpcode getJavaOpcode() {
        return javaOpcode;
    }

    public void setJavaOpcode(JavaOpcode javaOpcode) {
        this.javaOpcode = javaOpcode;
    }

    private static List<Class<?>> parse(String inputSignature) throws ClassNotFoundException {
        if ("()".equals(inputSignature)) {
            return Collections.emptyList();
        } else {
            String[] params = inputSignature.split(",");
            List<Class<?>> paramClasses = new ArrayList<>();
            for (String param : params) {
                paramClasses.add(Class.forName(param));
            }
            return paramClasses;
        }
    }
}

package nl.sander.bejava;

import nl.sander.bejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.bejava.constantpool.entry.FieldRefEntry;
import nl.sander.bejava.constantpool.entry.MethodRefEntry;

/**
 * bejava opcodes translate to real java opcodes. A JavaInstruct
 */
public class JavaInstruction {
    private final JavaOpcode opcode;
    private final MethodRefEntry methodRefEntry;
    private final FieldRefEntry fieldRefEntry;
    private final ConstantPoolEntry constantEntry;
    private Integer localVariableIndex; // TODO not used yet

    public JavaInstruction(JavaOpcode opcode) {
        this(opcode, null, null, null, null);
    }

    public JavaInstruction(JavaOpcode opcode, int localVariableIndex) {
        this(opcode, localVariableIndex, null, null, null);
    }

    public JavaInstruction(JavaOpcode opcode, ConstantPoolEntry constantEntry) {
        this(opcode, null, null, null, constantEntry);
    }

    public JavaInstruction(JavaOpcode opcode, MethodRefEntry methodRefEntry) {
        this(opcode, null, methodRefEntry, null, null);
    }

    public JavaInstruction(JavaOpcode opcode, FieldRefEntry fieldRefEntry) {
        this(opcode, null, null, fieldRefEntry, null);
    }

    private JavaInstruction(JavaOpcode opcode, Integer localVariableIndex, MethodRefEntry methodRefEntry, FieldRefEntry fieldRefEntry, ConstantPoolEntry constantEntry) {
        this.opcode = opcode;
        this.localVariableIndex = localVariableIndex;
        this.methodRefEntry = methodRefEntry;
        this.fieldRefEntry = fieldRefEntry;
        this.constantEntry = constantEntry;
    }

    public JavaOpcode getOpcode() {
        return opcode;
    }

    public MethodRefEntry getMethodRef() {
        return methodRefEntry;
    }

    public FieldRefEntry getFieldRef() {
        return fieldRefEntry;
    }

    public boolean hasMethodRef() {
        return methodRefEntry != null;
    }

    public boolean hasFieldRef() {
        return fieldRefEntry != null;
    }

    public ConstantPoolEntry getEntry() {
        if (methodRefEntry != null) {
            return methodRefEntry;
        } else if (fieldRefEntry != null) {
            return fieldRefEntry;
        } else if (constantEntry!=null){
            return constantEntry;
        } else {
            return null; // Not sure yet what to do here.
        }
    }

    public boolean hasConstantRef() {
        return constantEntry !=null;
    }

    public ConstantPoolEntry getConstantEntry() {
        return constantEntry;
    }
}

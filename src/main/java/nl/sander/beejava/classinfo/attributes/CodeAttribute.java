package nl.sander.beejava.classinfo.attributes;

import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.util.ByteBuf;

import java.util.HashSet;
import java.util.Set;

/**
 * §4.7.3 The Code Attribute
 */
public class CodeAttribute extends Attribute {

    private static final int MAX_STACK_LEN = 2; // nr of bytes for max_stack
    private static final int MAX_LOCALS_LEN = 2; // nr of bytes for max_locals
    private static final int CODE_LEN = 4; // nr of bytes for code_length
    private static final int NUM_EXCEPTION_HANDLERS_LEN = 2; // nr of bytes for number of exception_handlers
    private static final int NUM_ATTRIBUTES_LEN = 2; // nr of bytes for number of method attributes
    private static final int EXCEPTION_HANDLER_SIZE = 8; // nr of bytes per exception_hander

    private final Set<Attribute> attributes = new HashSet<>();
    private final Set<ExceptionHandler> exceptionHandlers = new HashSet<>();
    private int maxStack; // u2
    private int maxLocals; // u2
    private byte[] code;

    public CodeAttribute(Utf8Entry nameEntry) {
        super(nameEntry);
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] bytes) {
        this.code = bytes;
    }

    @Override
    public void addBytes(ByteBuf buf) {
        buf.addU16(nameEntry.getIndex());
        buf.addU32(MAX_STACK_LEN +
                MAX_LOCALS_LEN +
                CODE_LEN +
                code.length +
                NUM_EXCEPTION_HANDLERS_LEN +
                exceptionHandlers.size() * EXCEPTION_HANDLER_SIZE +
                NUM_ATTRIBUTES_LEN); //TODO works only if attributes are empty
        buf.addU16(maxStack);
        buf.addU16(maxLocals);
        buf.addU32(code.length);
        buf.addU8(getCode());
        buf.addU16(exceptionHandlers.size());
        buf.addU16(attributes.size());
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }
}

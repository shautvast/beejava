package nl.sander.beejava.apiv2;

import java.util.Optional;

public enum Opcode {
    LOAD,
    STORE,
    CONST,
    RETURN,
    ARRAYLENGTH,
    THROW,
    CAST,
    ADD,
    COMPARE,
    DIVIDE,
    MULTIPLY,
    NEGATE,
    REMAINDER,
    SUBTRACT,
    GET,
    GOTO,
    TO_DOUBLE,
    TO_INT,
    TO_LONG,
    TO_BYTE,
    TO_CHAR,
    TO_FLOAT,
    TO_SHORT,
    AND,
    IF_EQUAL,
    IF_NOT_EQUAL,
    IF_LESS_THAN,
    IF_GREATER_OR_EQUAL,
    IF_GREATER_THAN,
    IF_LESS_OR_EQUAL,
    IF_NOT_NULL,
    IF_NULL,
    INCREMENT,
    INSTANCEOF,
    INVOKE,
    OR,
    SHIFT_LEFT,
    SHIFT_RIGHT,
    LOGICAL_SHIFT_RIGHT,
    XOR,
    LOOKUPSWITCH,
    MONITORENTER,
    MONITOREXIT,
    NEW,
    NEWARRAY,
    MULTIANEWARRAY,
    PUT,
    SWAP,
    TABLESWITCH,
    WIDE;

    static Optional<Opcode> get(String text) {
        for (Opcode opcode : Opcode.values()) {
            if (opcode.toString().equals(text)) {
                return Optional.of(opcode);
            }
        }
        return Optional.empty();
    }
}

package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Opcode;
import nl.sander.beejava.flags.ClassAccessFlags;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static nl.sander.beejava.JavaOpcode.*;

public class OpcodeMapper {

    public static JavaOpcode mapOpcode(CodeLine codeLine) {
        Opcode opcode = codeLine.getOpcode();
        return switch (opcode) {
            case GET -> isStatic(codeLine.getExternalfield()) ? GETSTATIC : GETFIELD;
            case LD_VAR -> ALOAD_0;
            case LD_CONST -> loadConst(codeLine);
            case INVOKE -> invoke(codeLine);
            case RETURN -> JavaOpcode.RETURN; //TODO not complete yet
            default -> throw new IllegalStateException("something not implemented");
        };
    }

    /* TODO cover more cases */
    private static JavaOpcode invoke(CodeLine codeLine) {
        BeeSource source = codeLine.getOwner().getOwner();
        if (source.getAccessFlags().contains(ClassAccessFlags.SUPER) && codeLine.getOwner().isConstructor()) {
            return INVOKESPECIAL;
        } else {
            return INVOKEVIRTUAL;
        }
    }

    private static JavaOpcode loadConst(CodeLine codeLine) {
        Object constValue = codeLine.getConstValue();
        int index = codeLine.getAssignedEntry().getIndex();
        if (constValue instanceof Double || constValue instanceof Long) {
            return LDC2_W;
        } else {
            if (index > 0xff) {
                return LDC_W;
            } else {
                return LDC;
            }
        }
    }

    private static boolean isStatic(Field field) {
        return (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC;
    }
}

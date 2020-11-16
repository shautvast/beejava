package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Opcode;
import nl.sander.beejava.flags.ClassAccessFlags;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static nl.sander.beejava.JavaOpcodes.*;

public class OpcodeMapper {

    public static int mapOpcode(CodeLine codeLine) {
        Opcode opcode = codeLine.getOpcode();
        return switch (opcode) {
            case GET -> isStatic(codeLine.getExternalfield()) ? GETSTATIC : GETFIELD;
            case LD_VAR -> ALOAD_0;
            case LD_CONST -> loadConst(codeLine);
            case INVOKE -> invoke(codeLine);
            case RETURN -> JavaOpcodes.RETURN; //TODO not complete yet
            default -> 0;
        };
    }

    /* TODO cover more cases */
    private static int invoke(CodeLine codeLine) {
        BeeSource source = codeLine.getOwner().getOwner();
        if (source.getAccessFlags().contains(ClassAccessFlags.SUPER)) {
            return INVOKESPECIAL;
        } else {
            return INVOKEVIRTUAL;
        }
    }

    private static int loadConst(CodeLine codeLine) {
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

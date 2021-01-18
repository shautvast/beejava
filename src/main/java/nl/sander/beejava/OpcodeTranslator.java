package nl.sander.beejava;

import nl.sander.beejava.api.*;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.operands.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

enum Primitives {
    INT, SHORT, BYTE, LONG, FLOAT, DOUBLE, CHAR, BOOLEAN;

    public static Primitives from(String value) {
        return Arrays.stream(values()).filter(e -> e.toString().equals(value)).findFirst().orElse(null);
    }
}

/**
 * Translates from BeeJava opcodes to actual java opcodes.
 */
public class OpcodeTranslator {
    private final static OpcodeTranslator instance = new OpcodeTranslator();
    private final Parser parser = new Parser();
    private BeeSource beeSource;

    /*
     * trying to make SourceExpander reusable.
     * @not_multithreaded.
     * //TODO should not mutate beesource
     */
    public static void translate(BeeSource beeSource) {
        instance.init(beeSource);
        instance.doExpand();
    }

    private void init(BeeSource beeSource) {
        this.beeSource = beeSource;
    }

    private void doExpand() {
        beeSource.getConstructors().forEach(this::translate);
        beeSource.getMethods().forEach(this::translate);
    }

    private void translate(CodeContainer codeContainer) {
        var methodInstructions = codeContainer.getCode().stream()
                .flatMap(o -> translate(codeContainer, o).stream())
                .collect(Collectors.toList());
        codeContainer.setExpandedCode(methodInstructions);
    }

    private List<JavaInstruction> translate(CodeContainer codeContainer, CodeLine codeLine) {
        var operand = parser.parse(codeLine);
        return eval(codeContainer, codeLine.getOpcode(), operand);
    }

    private List<JavaInstruction> eval(CodeContainer codeContainer, Opcode opcode, Operand operand) {
        final List<JavaInstruction> instructions = new ArrayList<>();
        if (operand instanceof MethodOperand) {
            var mo = (MethodOperand) operand;
            if ("this".equals(mo.className)) {
                instructions.add(new JavaInstruction(JavaOpcode.ALOAD_0));
            }
        }
        if (opcode == Opcode.LOAD) {
            if (operand instanceof ConstantOperand) {
                ConstantPoolEntry constantEntry = ConstantPoolEntryCreator.getOrCreatePrimitiveConstantEntry((ConstantOperand) operand);
                instructions.add(new JavaInstruction(JavaOpcode.LDC, constantEntry));
            }
        } else if (opcode == Opcode.RETURN && operand instanceof VoidOperand) {
            instructions.add(new JavaInstruction(JavaOpcode.RETURN));
        } else if (opcode == Opcode.GET) {
            assert operand instanceof FieldOperand;

            var fieldOperand = (FieldOperand) operand;
            FieldWrapper field = getField(codeContainer, fieldOperand);
            instructions.add(new JavaInstruction(getJavaOpcodeForGet(field),
                    ConstantPoolEntryCreator.getOrCreateFieldRefEntry(getType(codeContainer, fieldOperand), field.getName(), field.getType())));
        } else if (opcode == Opcode.INVOKE) {
            assert operand instanceof MethodOperand;
            var mo = (MethodOperand) operand;
            if ("super".equals(mo.methodName)) {
                instructions.add(new JavaInstruction(JavaOpcode.INVOKESPECIAL,
                        ConstantPoolEntryCreator.getOrCreateMethodRefEntry(beeSource.getSuperClass().getName(), "<init>", "()V")));
            } else {
                instructions.add(new JavaInstruction(JavaOpcode.INVOKEVIRTUAL, ConstantPoolEntryCreator.getOrCreateMethodRefEntry(mo.className, mo.methodName, mo.signature)));
            }
            System.out.println(operand);
        } else if (operand instanceof FieldOperand) {
            var fieldOperand = (FieldOperand) operand;
            if (fieldOperand.className.equals("this")) {
                instructions.add(new JavaInstruction(JavaOpcode.ALOAD_0));
            }

            if (opcode == Opcode.RETURN) {
                instructions.add(getReturnInstruction(codeContainer, fieldOperand));
            }
        } else if (operand instanceof LocalVariableOperand) {
            var localVariable = (LocalVariableOperand) operand;
            instructions.add(codeContainer.getParameter(localVariable.name).map(this::getJavaLoadInstruction).orElse(null)); //else case
        }

        // TODO continue here. finish simpleBean
        return instructions;
    }

    private JavaOpcode getJavaOpcodeForGet(FieldWrapper field) {
        JavaOpcode get;
        if (Modifier.isStatic(field.getModifiers())) {
            get = JavaOpcode.GETSTATIC;
        } else {
            get = JavaOpcode.GETFIELD;
        }
        return get;
    }

    private JavaInstruction getReturnInstruction(CodeContainer codeContainer, FieldOperand fo) {
        FieldWrapper field = getField(codeContainer, fo);

        JavaOpcode javaOpcode = switch (field.getName()) {
            case "int", "byte", "boolean", "short" -> JavaOpcode.IRETURN;
            case "long" -> JavaOpcode.LRETURN;
            case "float" -> JavaOpcode.FRETURN;
            case "double" -> JavaOpcode.DRETURN;
            default -> JavaOpcode.ARETURN;
        };
        return new JavaInstruction(javaOpcode);

    }

    private String getType(CodeContainer codeContainer, FieldOperand fo) {
        if (fo.className.equals("this")) {
            return codeContainer.getName();
        } else {
            return fo.className;
        }
    }


    private FieldWrapper getField(CodeContainer codeContainer, FieldOperand fo) {
        try {
            Field javaField = null;
            BeeField beeField = null;
            if (fo.className.equals("this")) {
                beeField = codeContainer.getOwner().getField(fo.fieldName);
            } else {
                Class<?> type = Class.forName(fo.className);
                javaField = type.getDeclaredField(fo.fieldName);
            }
            return new FieldWrapper(javaField, beeField);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }

    private JavaInstruction getJavaLoadInstruction(BeeParameter parameter) {
        return switch (parameter.getIndex()) {
            case 0 -> new JavaInstruction(JavaOpcode.ALOAD_0);
            case 1 -> new JavaInstruction(JavaOpcode.ALOAD_1);
            case 2 -> new JavaInstruction(JavaOpcode.ALOAD_2);
            case 3 -> new JavaInstruction(JavaOpcode.ALOAD_3);
            default -> new JavaInstruction(JavaOpcode.ALOAD, parameter.getIndex());
        };
    }
}

class Parser {

    Operand parse(CodeLine codeLine) {
        var operand = codeLine.getOperand();
        if (operand == null) {
            return VoidOperand.INSTANCE;
        } else {
            if (operand.contains("(")) {
                var parenIndex = operand.indexOf('(');
                var signature = operand.substring(parenIndex);
                var classAndMethodeName = operand.substring(0, parenIndex);
                int lastDotIndex = classAndMethodeName.lastIndexOf('.');
                var className = classAndMethodeName.substring(0, lastDotIndex);
                var methodName = classAndMethodeName.substring(lastDotIndex + 1);
                return new MethodOperand(className, methodName, signature);
            } else {
                int index = operand.lastIndexOf('.');
                if (index < 0) {
                    var constantOperandOrNull = asConstantOperand(operand);
                    return Objects.requireNonNullElseGet(constantOperandOrNull, () -> new LocalVariableOperand(operand));
                } else if (index < operand.length() - 1) {
                    var className = operand.substring(0, index);
                    var fieldName = operand.substring(index + 1);
                    return new FieldOperand(className, fieldName);
                }
            }
        }
        throw new IllegalArgumentException("compile error");
    }

    ConstantOperand asConstantOperand(String value) {
        //TODO add boxed types
        var tokens = value.split(" ");
        var type = Primitive.from(tokens[0].toUpperCase(Locale.ROOT));
        if (tokens.length == 2 && type != null) {
            return new ConstantOperand(type, tokens[1]);
        } else if (value.startsWith("\"")) {
            if (value.endsWith("\"")) {
                return new ConstantOperand(Primitive.STRING, value.substring(1, value.length() - 1));
            } else {
                throw new IllegalArgumentException("Unterminated String value");
            }
        }
        return null;
    }
}
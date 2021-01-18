package nl.sander.beejava;

import nl.sander.beejava.api.*;
import nl.sander.beejava.flags.ClassAccessFlags;
import nl.sander.beejava.flags.FieldAccessFlag;
import nl.sander.beejava.flags.MethodAccessFlag;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses code as text to a BeeSource object
 */
public class SourceCompiler {
    private final static Pattern firstBlanksplitter = Pattern.compile("(.+?) (.+)");
    private final static Pattern parensplitter = Pattern.compile("(.+?)\\((.*?)\\)");
    private final static Pattern returntypesplitter = Pattern.compile("->");
    private final String sourcecode;
    private final List<Instruction> instructions = new ArrayList<>();
    private int currentLine = 0;

    public SourceCompiler(String sourcecode) {
        this.sourcecode = sourcecode;
    }

    public static BeeSource compile(String sourcecode) {
        return new SourceCompiler(sourcecode).doCompile();
    }

    public BeeSource doCompile() {
        Arrays.stream(sourcecode.split("\n"))
                .map(this::compileLine)
                .forEach(instructions::add);

        BeeSource beeSource = new BeeSource();

        for (currentLine = 0; currentLine < instructions.size(); ) {
            Instruction ins = instructions.get(currentLine);
            if (currentLine == 0) {
                parseClassDeclaration(ins, beeSource);
            } else {
                parseInstruction(ins, beeSource);
            }
        }

        return beeSource;
    }

    private void parseInstruction(Instruction instruction, BeeSource beeSource) {
        if (instruction instanceof ClassInstruction) {
            ClassInstruction classInstruction = (ClassInstruction) instruction;
            String operand = classInstruction.getOperand();
            switch (classInstruction.getOperation()) {
                case FIELD -> beeSource.addFields(parseField(beeSource, operand));
                case CONSTRUCTOR -> beeSource.addConstructors(parseConstructor(beeSource, operand));
                case METHOD -> beeSource.addMethods(parseMethod(beeSource, operand));
                default -> throw new IllegalArgumentException("Not allowed here");
            }
        }
    }

    private nl.sander.beejava.api.BeeMethod parseMethod(BeeSource beeSource, String text) {
        String[] tokens = returntypesplitter.split(text);
        final String first;
        final Class<?> returnType;
        if (tokens.length > 1) {
            returnType = getType(tokens[1].trim());
            first = tokens[0].trim();
        } else {
            first = text;
            returnType = Void.TYPE;
        }

        String[] flagsNameParameters = split(first, firstBlanksplitter);

        Set<MethodAccessFlag> flags = new HashSet<>();
        int i = 0;
        Optional<MethodAccessFlag> maybeFlag = MethodAccessFlag.get(flagsNameParameters[i].toUpperCase());
        while (maybeFlag.isPresent()) {
            flags.add(maybeFlag.get());
            i += 1;
            maybeFlag = MethodAccessFlag.get(flagsNameParameters[i]);
        }

        String[] nameParams = split(flagsNameParameters[i], parensplitter);
        Set<nl.sander.beejava.api.BeeParameter> parameters = new HashSet<>();
        String methodName = null;
        if (nameParams.length > 0) {
            methodName = nameParams[0];
            if (nameParams[1].length() > 0) {
                int index = 0;
                String params = nameParams[1];
                String[] paramTokens = params.split(",");
                for (String paramToken : paramTokens) {
                    String[] declaration = paramToken.trim().split(" ");
                    String type = declaration[0];
                    String name = declaration[1];
                    parameters.add(new nl.sander.beejava.api.BeeParameter(getType(type), name, index++));
                }
            }
        }
        if (methodName == null) {
            throw new IllegalArgumentException("method name not found");
        }
        currentLine += 1;
        List<nl.sander.beejava.api.CodeLine> lines = new ArrayList<>();
        Instruction nextInstruction = instructions.get(currentLine);
        while (currentLine < instructions.size() && nextInstruction instanceof CodeLine) {
            nextInstruction = instructions.get(currentLine);
            if (nextInstruction instanceof CodeLine) {
                lines.add((CodeLine) nextInstruction);
                currentLine += 1;
            }
        }


        return new BeeMethod(beeSource, methodName, flags, parameters, returnType, lines);
    }

    private nl.sander.beejava.api.BeeConstructor parseConstructor(BeeSource beeSource, String text) {
        String[] tokens = split(text, parensplitter);
        String flag = tokens[0];
        MethodAccessFlag methodAccessFlag = MethodAccessFlag.get(flag.toUpperCase()).orElseThrow(illegalArgument("Not a valid flag " + flag));

        String params = tokens[1];
        Set<BeeParameter> parameters = new HashSet<>();
        if (params.length() > 0) {
            String[] paramTokens = params.split(",");
            int index = 0;
            for (String paramToken : paramTokens) {
                String[] declaration = paramToken.trim().split(" ");
                String type = declaration[0];
                String name = declaration[1];
                parameters.add(new BeeParameter(getType(type), name, index++));
            }
        }
        currentLine += 1;
        List<CodeLine> lines = new ArrayList<>();
        Instruction nextInstruction = instructions.get(currentLine);
        while (currentLine < instructions.size() && nextInstruction instanceof CodeLine) {
            nextInstruction = instructions.get(currentLine);
            if (nextInstruction instanceof CodeLine) {
                lines.add((CodeLine) nextInstruction);
                currentLine += 1;
            }
        }

        return new BeeConstructor(beeSource, Set.of(methodAccessFlag), parameters, lines);
    }

    private nl.sander.beejava.api.BeeField parseField(BeeSource beeSource, String operand) {
        String[] tokens = operand.split(" ");
        Set<FieldAccessFlag> flags = new HashSet<>();
        String type = null;
        String name = null;
        for (String token : tokens) {
            Optional<FieldAccessFlag> maybeFlag = FieldAccessFlag.get(token.toUpperCase());
            if (maybeFlag.isPresent()) {
                flags.add(maybeFlag.get());
            } else {
                if (type == null) {
                    type = token;
                } else {
                    name = token;
                }
            }
        }
        currentLine += 1;
        return new BeeField(beeSource.getName(), flags, getType(type), name);
    }

    private void parseClassDeclaration(Instruction instruction, BeeSource beeSource) {
        if (instruction instanceof ClassInstruction) {
            ClassInstruction classDeclaration = (ClassInstruction) instruction;
            ClassOperation operation = classDeclaration.getOperation();
            switch (operation) {
                case CLASS -> {
                    beeSource.addAccessFlags(ClassAccessFlags.SUPER);
                    beeSource.setClassFileVersion(getVersion(classDeclaration));

                    String[] tokens = split(classDeclaration.getOperand(), parensplitter);
                    String rightHand = classDeclaration.getOperand();
                    if (tokens.length > 0) { // has Version tag
                        rightHand = tokens[0];
                    }
                    tokens = rightHand.split(" ");

                    if (tokens.length == 2) { // has access flag
                        beeSource.addAccessFlags(ClassAccessFlags.valueOf(tokens[0].toUpperCase()));
                    }
                    beeSource.setName(getClassName(tokens));
                }
                case INTERFACE -> {
                }//TODO
                case ENUM -> {
                }//TODO
                default -> throw new IllegalArgumentException("Not allowed here");
            }
        } else {
            throw new IllegalArgumentException("class must start with 'class name(target.jdk.version)'");
        }
        currentLine += 1;
    }

    private Version getVersion(ClassInstruction classDeclaration) {
        String[] tokens2 = split(classDeclaration.getOperand(), parensplitter);
        if (tokens2.length == 0) {
            return Version.V8;
        } else {
            return Version.get(tokens2[1]).orElseThrow(illegalArgument(tokens2[1]));
        }
    }

    private String getClassName(String[] tokens) {
        if (tokens.length == 2) {
            return tokens[1];
        } else {
            return tokens[0];
        }
    }

    private Instruction compileLine(String line) {
        if (!line.startsWith("  ")) {
            String[] tokens = split(line, firstBlanksplitter);
            String operation = tokens[0];
            String operand = tokens[1];
            ClassOperation classOperation = ClassOperation.get(operation).orElseThrow(illegalArgument(operation));
            return new ClassInstruction(classOperation, operand);
        } else {
            line = line.substring(2);
            if (line.startsWith(" ")) {
                throw new IllegalArgumentException("Illegal indent -> must be 2 spaces");
            }
            String operation;
            String operand;
            if (line.indexOf(' ') > -1) {
                String[] tokens = split(line, firstBlanksplitter);

                operation = tokens[0];
                operand = tokens[1];
            } else {
                operation = line;
                operand = null;
            }
            Opcode opcode = Opcode.get(operation).orElseThrow(illegalArgument("Illegal opcode: " + operation));
            return new CodeLine(opcode, operand);
        }
    }

    private Supplier<IllegalArgumentException> illegalArgument(String text) {
        return () -> new IllegalArgumentException(text);
    }

    private String[] split(String text, Pattern splitter) {
        Matcher matcher = splitter.matcher(text);
        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        } else {
            return new String[]{};
        }
    }

    private Class<?> getType(String type) {
        try {
            return switch (type) {
                case "int" -> int.class;
                case "short" -> short.class;
                case "byte" -> byte.class;
                case "long" -> long.class;
                case "float" -> float.class;
                case "double" -> double.class;
                case "boolean" -> boolean.class;
                case "char" -> char.class;
                default -> Class.forName(type);
            };
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Not a valid type: " + type);
        }
    }
}

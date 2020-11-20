package nl.sander.beejava.apiv2;

import nl.sander.beejava.api.Version;
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
        Arrays.stream(sourcecode.split("\n")).map(this::compileLine).forEach(instructions::add);

        BeeSource beeSource = new nl.sander.beejava.apiv2.BeeSource();

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
                case FIELD -> beeSource.addFields(parseField(operand));
                case CONSTRUCTOR -> beeSource.addConstructors(parseConstructor(operand));
                case METHOD -> beeSource.addMethods(parseMethod(operand));
                default -> throw new IllegalArgumentException("Not allowed here");
            }
        }
    }

    private BeeMethod parseMethod(String text) {
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
        Set<BeeParameter> parameters = new HashSet<>();
        String methodName=null;
        if (nameParams.length > 0) {
            methodName = nameParams[0];
            if (nameParams[1].length() > 0) {

                String params = nameParams[1];
                String[] paramTokens = params.split(",");
                for (String paramToken : paramTokens) {
                    String[] declaration = paramToken.trim().split(" ");
                    String type = declaration[0];
                    String name = declaration[1];
                    parameters.add(new BeeParameter(getType(type), name));
                }
            }
        }
        if (methodName==null){
            throw new IllegalArgumentException("method name not found");
        }
        currentLine += 1;
        List<CodeLine> lines = new ArrayList<>();
        Instruction nextInstruction = instructions.get(currentLine);
        while (nextInstruction instanceof CodeLine) {
            lines.add((CodeLine) nextInstruction);
            currentLine += 1;
            if (currentLine >= instructions.size()) {
                break; // too tired to think
            }
            nextInstruction = instructions.get(currentLine);
        }


        return new BeeMethod(methodName, flags, parameters, returnType, lines);
    }

    private BeeConstructor parseConstructor(String text) {
        String[] tokens = split(text, parensplitter);
        String flag = tokens[0];
        MethodAccessFlag methodAccessFlag = MethodAccessFlag.get(flag.toUpperCase()).orElseThrow(illegalArgument("Not a valid flag " + flag));

        String params = tokens[1];
        Set<BeeParameter> parameters = new HashSet<>();
        if (params.length() > 0) {
            String[] paramTokens = params.split(",");
            for (String paramToken : paramTokens) {
                String[] declaration = paramToken.trim().split(" ");
                String type = declaration[0];
                String name = declaration[1];
                try {
                    parameters.add(new BeeParameter(Class.forName(type), name));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("field type " + type + " not found");
                }
            }
        }
        currentLine += 1;
        List<CodeLine> lines = new ArrayList<>();
        Instruction nextInstruction = instructions.get(currentLine);
        while (nextInstruction instanceof CodeLine) {
            lines.add((CodeLine) nextInstruction);
            currentLine += 1;
            nextInstruction = instructions.get(currentLine);

        }
        return new BeeConstructor(Set.of(methodAccessFlag), parameters, lines);
    }

    private BeeField parseField(String operand) {
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
        return new BeeField(flags, getType(type), name);
    }

    private void parseClassDeclaration(Instruction firstLine, BeeSource beeSource) {
        if (firstLine instanceof ClassInstruction) {
            ClassInstruction classDeclaration = (ClassInstruction) firstLine;
            ClassOperation operation = classDeclaration.getOperation();
            switch (operation) {
                case CLASS -> {
                    String[] tokens = split(classDeclaration.getOperand(), parensplitter);
                    beeSource.addAccessFlags(ClassAccessFlags.PUBLIC, ClassAccessFlags.SUPER);
                    beeSource.setName(tokens[0]);
                    beeSource.setClassFileVersion(Version.get(tokens[1]).orElseThrow(illegalArgument(tokens[1])));
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

    private Instruction compileLine(String line) {
        if (!line.startsWith("  ")) {
            String[] tokens = split(line, firstBlanksplitter);
            String operation = tokens[0];
            String operand = tokens[1];
            ClassOperation classOperation = ClassOperation.get(operation).orElseThrow(illegalArgument(operation));
            return new ClassInstruction(classOperation, operand);
        } else {
            line = line.substring(2);
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
                case "double" -> double.class;
                default -> Class.forName(type);
            };
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Not a valid type: " + type);
        }
    }
}

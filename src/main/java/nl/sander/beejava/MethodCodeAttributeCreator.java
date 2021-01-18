package nl.sander.beejava;

import nl.sander.beejava.api.CodeContainer;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.classinfo.attributes.CodeAttribute;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.constantpool.entry.MethodRefEntry;
import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.util.ByteBuf;

public class MethodCodeAttributeCreator {

    public static CodeAttribute createCodeAttribute(Utf8Entry codeAttributeNameEntry, CodeContainer codeContainer) {
        CodeAttribute codeAttribute = new CodeAttribute(codeAttributeNameEntry);
        codeAttribute.setMaxStack(calculateMaxStack(codeContainer));
        codeAttribute.setMaxLocals(codeContainer.getFormalParameters().size() + 1);
        ByteBuf byteBuf = new ByteBuf();

        codeContainer.getExpandedCode().forEach(instruction -> {
            JavaOpcode javaOpcode = instruction.getOpcode();
            byteBuf.addU8(javaOpcode.getByteCode());
            ConstantPoolEntry constantPoolEntry = instruction.getEntry();
            if (constantPoolEntry != null) {
                if (javaOpcode.isWide()) {
                    byteBuf.addU16(constantPoolEntry.getIndex());
                } else if (constantPoolEntry.getIndex() < 0x100) {
                    byteBuf.addU8(constantPoolEntry.getIndex());
                } else {
                    throw new IllegalStateException("cannot use non-wide opcode with wide constantpool index");
                }
            }
        });
        codeAttribute.setCode(byteBuf.toBytes());
        return codeAttribute;
    }

    private static int calculateMaxStack(CodeContainer codeContainer) {
        int stackSize = 0;
        int maxStackSize = 0;
        for (JavaInstruction instruction : codeContainer.getExpandedCode()) {
            stackSize += instruction.getOpcode().getStackDif();
            ConstantPoolEntry assignedEntry = instruction.getEntry();
            if (assignedEntry instanceof MethodRefEntry) {
                MethodRefEntry methodRefEntry = (MethodRefEntry) assignedEntry;
                String argumentTypes = methodRefEntry.getNameAndType().getType();
                stackSize -= getNrArguments(argumentTypes);
            }
            if (stackSize > maxStackSize) {
                maxStackSize = stackSize;
            }
        }

        return maxStackSize;
    }

    private static int getNrArguments(String argumentTypes) {
        int nr = 0;
        for (int i = 0; i < argumentTypes.length(); i++) {
            if (argumentTypes.charAt(i) == ',') {
                nr += 1;
            }
        }
        return nr;
    }
}

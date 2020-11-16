package nl.sander.beejava;

import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.classinfo.attributes.CodeAttribute;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.util.ByteBuf;

import java.util.List;

public class MethodCodeCreator {

    public static CodeAttribute createCodeAttribute(Utf8Entry codeAttributeNameEntry, List<CodeLine> code) {
        CodeAttribute codeAttribute = new CodeAttribute(codeAttributeNameEntry);
        codeAttribute.setMaxStack(1);
        codeAttribute.setMaxLocals(1);
        ByteBuf byteBuf = new ByteBuf();
        code.forEach(codeLine -> {
            ConstantPoolEntry constantPoolEntry = codeLine.getAssignedEntry();
            byteBuf.addU8(OpcodeMapper.mapOpcode(codeLine));
            if (constantPoolEntry != null) {
                byteBuf.addU16(constantPoolEntry.getIndex());
            }
        });
        codeAttribute.setCode(byteBuf.toBytes());
        return codeAttribute;
    }
}

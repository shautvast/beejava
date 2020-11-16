package nl.sander.beejava;

import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.flags.AccessFlags;
import nl.sander.beejava.util.ByteBuf;

public class BytecodeGenerator {

    private final CompiledClass compiledClass; // maybe not a member?

    public BytecodeGenerator(CompiledClass compiledClass) {
        this.compiledClass = compiledClass;
    }

    public static byte[] generate(CompiledClass compiledClass) {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator(compiledClass);
        return bytecodeGenerator.generate();
    }

    private byte[] generate() {
        ByteBuf buf = new ByteBuf();
        buf.addU8(0xCA, 0xFE, 0xBA, 0xBE);
        buf.addU16(compiledClass.getSource().getClassFileVersion().getMinor());
        buf.addU16(compiledClass.getSource().getClassFileVersion().getMajor());

        buf.addU16(compiledClass.getConstantPool().getLength());
        compiledClass.getConstantPool().addTo(buf);
        buf.addU16(AccessFlags.combine(compiledClass.getSource().getAccessFlags()));
        buf.addU16(compiledClass.geThisIndex());
        buf.addU16(compiledClass.getSuperIndex());
        buf.addU16(compiledClass.getInterfaces().size());
        compiledClass.getInterfaces().forEach(interfase -> buf.addU16(interfase.getIndex()));
        buf.addU16(compiledClass.getFields().size());
        compiledClass.getFields().forEach(fieldInfo -> fieldInfo.addBytes(buf));
        buf.addU16(compiledClass.getMethods().size());
        compiledClass.getMethods().forEach(methodInfo -> methodInfo.addBytes(buf));
        buf.addU16(0); //attributes count
        return buf.toBytes();
    }

}

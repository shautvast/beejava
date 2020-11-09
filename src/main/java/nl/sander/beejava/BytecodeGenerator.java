package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.flags.ClassAccessFlag;
import nl.sander.beejava.util.ByteBuf;

public class BytecodeGenerator {

    private final BeeClass beeClass; // maybe not a member?
    private final Compiler compiler = new Compiler();
    private final ConstantPoolCreator constantPoolCreator = new ConstantPoolCreator();

    public BytecodeGenerator(BeeClass beeClass) {
        this.beeClass = beeClass;
    }

    public static byte[] compile(BeeClass beeClass) {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator(beeClass);
        return bytecodeGenerator.doCompile();
    }

    private byte[] doCompile() {
        ByteBuf buf = new ByteBuf();
        buf.addU8(0xCA, 0xFE, 0xBA, 0xBE);
        buf.addU16(beeClass.getClassFileVersion().getMinor());
        buf.addU16(beeClass.getClassFileVersion().getMajor());

        CompiledClass compiledClass = compiler.compile(beeClass);
        ConstantPool constantPool = constantPoolCreator.createConstantPool(compiledClass.getConstantTree());

        buf.addU16(constantPool.getLength());
        buf.addU8(constantPool.getBytes());
        buf.addU16(ClassAccessFlag.getSum(beeClass.getAccessFlags()));
        buf.addU16(compiledClass.geThisIndex());
        buf.addU16(compiledClass.getSuperIndex());
        buf.addU16(compiledClass.getInterfaces().size());
        compiledClass.getInterfaces().forEach(interfase -> buf.addU16(interfase.getIndex()));

        int x = 1;
        for (ConstantPoolEntry e : constantPool) {
            System.out.println((x++) + ":" + e);
        }
        printBytes(buf);

        return buf.toBytes();
    }

    //TODO remove
    private void printBytes(ByteBuf buf) {
        byte[] bytes = buf.toBytes();
        int count = 0;
        for (byte b : bytes) {
            System.out.print(String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0') + (count % 2 == 0 ? "" : " "));
            if (++count > 15) {
                count = 0;
                System.out.println();
            }
        }
    }


}

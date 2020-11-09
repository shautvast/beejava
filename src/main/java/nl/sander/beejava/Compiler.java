package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.NodeConstant;
import nl.sander.beejava.util.ByteBuf;

import java.util.Set;

public class Compiler {

    private final BeeClass beeClass; // maybe not a member?
    private final ConstantTreeCreator constantTreeCreator = new ConstantTreeCreator();
    private final ConstantPoolCreator constantPoolCreator = new ConstantPoolCreator();

    public Compiler(BeeClass beeClass) {
        this.beeClass = beeClass;
    }

    public static byte[] compile(BeeClass beeClass) {
        Compiler compiler = new Compiler(beeClass);
        return compiler.doCompile();
    }

    private byte[] doCompile() {
        ByteBuf buf = new ByteBuf();
        buf.addU8(0xCA, 0xFE, 0xBA, 0xBE);
        buf.addU16(beeClass.getClassFileVersion().getMinor());
        buf.addU16(beeClass.getClassFileVersion().getMajor());

        Set<NodeConstant> constantTree = constantTreeCreator.createConstantTree(beeClass);
        ConstantPool constantPool = constantPoolCreator.createConstantPool(constantTree);

        buf.addU16(constantPool.getLength());
        buf.addU8(constantPool.getBytes());



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

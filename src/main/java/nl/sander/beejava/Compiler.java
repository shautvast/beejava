package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
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
        buf.add(0xCA, 0xFE, 0xBA, 0xBE);
        buf.add(beeClass.getClassFileVersion().getMinor());
        buf.add(beeClass.getClassFileVersion().getMajor());

        Set<ConstantPoolEntry> constantTree = constantTreeCreator.createConstantTree(beeClass);
        ConstantPool constantPool = constantPoolCreator.createConstantPool(constantTree);

        buf.add(constantPool.getBytes());
        return buf.toBytes();
    }


}

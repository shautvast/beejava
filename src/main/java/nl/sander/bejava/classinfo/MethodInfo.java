package nl.sander.bejava.classinfo;

import nl.sander.bejava.constantpool.entry.Utf8Entry;
import nl.sander.bejava.flags.AccessFlags;
import nl.sander.bejava.flags.MethodAccessFlag;
import nl.sander.bejava.util.ByteBuf;

import java.util.HashSet;
import java.util.Set;

/**
 * Conforms to method_info struct in JVM specification
 * See 4.1 The ClassFile Structure
 */
public class MethodInfo extends Info<MethodInfo> {
    private final Set<MethodAccessFlag> accessFlags = new HashSet<>();

    public MethodInfo(Utf8Entry nameEntry, Utf8Entry descriptorEntry) {
        super(nameEntry, descriptorEntry);
    }
    public MethodInfo addAccessFlags(Set<MethodAccessFlag> accessFlags) {
        this.accessFlags.addAll(accessFlags);
        return this;
    }
    public void addBytes(ByteBuf buf) {
        buf.addU16(AccessFlags.combine(accessFlags));
        buf.addU16(nameEntry.getIndex());
        buf.addU16(descriptorEntry.getIndex());
        buf.addU16(attributes.size());
        attributes.forEach(ai -> ai.addBytes(buf));
    }
}

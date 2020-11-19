package nl.sander.beejava.classinfo;

import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.flags.AccessFlags;
import nl.sander.beejava.flags.MethodAccessFlag;
import nl.sander.beejava.util.ByteBuf;

import java.util.HashSet;
import java.util.Set;

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

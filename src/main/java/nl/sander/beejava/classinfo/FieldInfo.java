package nl.sander.beejava.classinfo;

import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.flags.AccessFlags;
import nl.sander.beejava.flags.FieldAccessFlag;
import nl.sander.beejava.util.ByteBuf;

import java.util.HashSet;
import java.util.Set;

public class FieldInfo extends Info<FieldInfo> {
    private final Set<FieldAccessFlag> accessFlags = new HashSet<>();

    public FieldInfo(Utf8Entry nameEntry, Utf8Entry descriptorEntry) {
        super(nameEntry, descriptorEntry);
    }

    public FieldInfo addAccessFlags(Set<FieldAccessFlag> accessFlags) {
        this.accessFlags.addAll(accessFlags);
        return this;
    }

    public Set<FieldAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    public void addBytes(ByteBuf buf) {
        buf.addU16(AccessFlags.combine(accessFlags));
        buf.addU16(nameEntry.getIndex());
        buf.addU16(descriptorEntry.getIndex());
        buf.addU16(attributes.size());
        attributes.forEach(ai -> ai.addBytes(buf));
    }
}

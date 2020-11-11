package nl.sander.beejava.classinfo;

import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.flags.AccessFlags;
import nl.sander.beejava.flags.FieldAccessFlags;
import nl.sander.beejava.util.ByteBuf;

import java.util.HashSet;
import java.util.Set;

public class FieldInfo {
    private final Set<FieldAccessFlags> accessFlags=new HashSet<>();
    private final Utf8Entry nameEntry;
    private final Utf8Entry descriptorEntry;
    private final Set<AttributeInfo> attributes=new HashSet<>();


    public FieldInfo(Utf8Entry nameEntry, Utf8Entry descriptorEntry) {
        this.nameEntry = nameEntry;
        this.descriptorEntry = descriptorEntry;
    }
    public FieldInfo addAccessFlags(Set<FieldAccessFlags> accessFlags){
        this.accessFlags.addAll(accessFlags);
        return this;
    }

    public Set<FieldAccessFlags> getAccessFlags() {
        return accessFlags;
    }

    public Utf8Entry getNameEntry() {
        return nameEntry;
    }

    public Utf8Entry getDescriptorEntry() {
        return descriptorEntry;
    }

    public Set<AttributeInfo> getAttributes() {
        return attributes;
    }

    public void addBytes(ByteBuf buf) {
        buf.addU16(AccessFlags.combine(accessFlags));
        buf.addU16(nameEntry.getIndex());
        buf.addU16(descriptorEntry.getIndex());
        buf.addU16(attributes.size());
        attributes.forEach(ai -> ai.addBytes(buf));
    }
}

package nl.sander.beejava.classinfo;

import nl.sander.beejava.classinfo.attributes.Attribute;
import nl.sander.beejava.constantpool.entry.Utf8Entry;

import java.util.HashSet;
import java.util.Set;

public abstract class Info<T extends Info<T>> {

    protected final Utf8Entry nameEntry;
    protected final Utf8Entry descriptorEntry;
    protected final Set<Attribute> attributes = new HashSet<>();

    public Info(Utf8Entry nameEntry, Utf8Entry descriptorEntry) {
        this.nameEntry = nameEntry;
        this.descriptorEntry = descriptorEntry;
    }

    public Utf8Entry getNameEntry() {
        return nameEntry;
    }

    public Utf8Entry getDescriptorEntry() {
        return descriptorEntry;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public T addAttribute(Attribute attribute) {
        attributes.add(attribute);
        return (T) this;
    }
}

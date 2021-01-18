package nl.sander.bejava.classinfo;

import nl.sander.bejava.classinfo.attributes.Attribute;
import nl.sander.bejava.constantpool.entry.Utf8Entry;

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

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public T addAttribute(Attribute attribute) {
        attributes.add(attribute);
        return (T) this;
    }
}

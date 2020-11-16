package nl.sander.beejava.classinfo.attributes;

import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.util.ByteBuf;

public abstract class Attribute {
    protected final Utf8Entry nameEntry;
    protected int length;

    protected Attribute(Utf8Entry nameEntry) {
        this.nameEntry = nameEntry;
    }

    public abstract void addBytes(ByteBuf buf);
}

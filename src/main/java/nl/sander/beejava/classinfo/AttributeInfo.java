package nl.sander.beejava.classinfo;

import nl.sander.beejava.constantpool.entry.Utf8Entry;
import nl.sander.beejava.util.ByteBuf;

public abstract class AttributeInfo {
    private Utf8Entry nameEntry;
    private int length;

    public abstract void addBytes(ByteBuf buf);
}

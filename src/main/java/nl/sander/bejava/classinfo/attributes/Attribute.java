package nl.sander.bejava.classinfo.attributes;

import nl.sander.bejava.constantpool.entry.Utf8Entry;
import nl.sander.bejava.util.ByteBuf;

/**
 * attribute_info struct as in the JVM spec.
 * See 4.1 The ClassFile Structure.
 */
public abstract class Attribute {
    protected final Utf8Entry nameEntry;
    protected int length;

    protected Attribute(Utf8Entry nameEntry) {
        this.nameEntry = nameEntry;
    }

    public abstract void addBytes(ByteBuf buf);
}

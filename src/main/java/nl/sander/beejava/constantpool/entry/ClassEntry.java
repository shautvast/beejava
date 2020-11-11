package nl.sander.beejava.constantpool.entry;

import java.util.Objects;

public class ClassEntry extends ConstantPoolEntry {
    private static final byte TAG = 7;
    private final Utf8Entry name;

    public ClassEntry(Utf8Entry name) {
        super(name);
        this.name = name;
    }

    public int getNameIndex() {
        return name.getIndex();
    }

    @Override
    public String toString() {
        return "Class\t\t\t#" + getNameIndex() + "\t\t// " + name.getUtf8();
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{TAG, upperByte(getNameIndex()), lowerByte(getNameIndex())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassEntry that = (ClassEntry) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name.getUtf8();
    }
}

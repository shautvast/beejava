package nl.sander.bejava.api;

import java.util.Objects;

/**
 * Models a formal parameter in a method declaration.
 */
public final class BeParameter {
    private final Class<?> type;
    private final String name;
    private final int index;

    public BeParameter(Class<?> type, String name) {
        this.type = type;
        this.name = name;
        this.index = -1;
    }

    public BeParameter(Class<?> type, String name, int index) {
        this.type = type;
        this.name = name;
        this.index = index;
    }

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (BeParameter) o;
        return name.equals(that.name);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

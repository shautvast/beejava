package nl.sander.beejava.api;

import java.util.Objects;

/**
 * Models a formal parameter in a method declaration.
 */
public class BeeParameter {
    private final Class<?> type;
    private final String name;

    private BeeParameter(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }

    public static BeeParameter create(Class<?> type, String name) {
        return new BeeParameter(type, Objects.requireNonNull(name));
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
        BeeParameter that = (BeeParameter) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

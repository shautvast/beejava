package nl.sander.beejava.api;

import nl.sander.beejava.flags.FieldAccessFlag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Models a field in a BeeClass
 */
public final class BeeField {

    private final String declaringClass;
    private final Set<FieldAccessFlag> accessFlags = new HashSet<>();
    private final Class<?> type;
    private final String name;

    /**
     *
     * @param declaringClass class that declares the field. Can be existing class, or the one that is under construction
     * @param accessFlags
     * @param type field type. Must be existing type.
     * @param name
     */
    public BeeField(String declaringClass, Set<FieldAccessFlag> accessFlags, Class<?> type, String name) {
        this.declaringClass = declaringClass;
        this.accessFlags.addAll(accessFlags);
        this.type = type;
        this.name = name;
    }

    public Set<FieldAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeeField beeField = (BeeField) o;
        return name.equals(beeField.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}

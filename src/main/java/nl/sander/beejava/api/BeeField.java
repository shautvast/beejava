package nl.sander.beejava.api;

import nl.sander.beejava.flags.FieldAccessFlag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BeeField {

    private final Set<FieldAccessFlag> accessFlags = new HashSet<>();
    private final Class<?> type;
    private final String name;

    private BeeField(Set<FieldAccessFlag> accessFlags, Class<?> type, String name) {
        this.accessFlags.addAll(accessFlags);
        this.type = type;
        this.name = name;
    }

    public static BeeField.Builder builder(){
        return new Builder();
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

    public static class Builder {
        private final Set<FieldAccessFlag> accessFlags = new HashSet<>();
        private Class<?> type;
        private String name;

        private Builder(){

        }

        public BeeField.Builder withAccessFlags(FieldAccessFlag... accessFlags) {
            this.accessFlags.addAll(Arrays.asList(accessFlags));
            return this;
        }

        public BeeField.Builder withType(Class<?> type) {
            this.type=type;
            return this;
        }

        public BeeField.Builder withName(String name) {
            this.name=name;
            return this;
        }

        public BeeField build() {
            return new BeeField(accessFlags, type, name);
        }
    }
}

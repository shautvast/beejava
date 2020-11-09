package nl.sander.beejava;

import nl.sander.beejava.flags.ClassAccessFlag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BeeClass {
    private final Version classFileVersion;
    private final BeePackage beePackage;
    private final Set<ClassAccessFlag> accessFlags = new HashSet<>();
    private final String name;
    private final Class<?> superClass;
    private final Set<Class<?>> interfaces = new HashSet<>();
    private final Set<BeeField> fields = new HashSet<>();
    private final Set<BeeConstructor> constructors = new HashSet<>();

    private BeeClass(Version classFileVersion,
                     BeePackage beePackage, Set<ClassAccessFlag> accessFlags, String name, Class<?> superClass,
                     Set<Class<?>> interfaces, Set<BeeField> fields, Set<BeeConstructor> constructors) {
        this.classFileVersion = classFileVersion;
        this.beePackage = beePackage;
        this.accessFlags.addAll(accessFlags);
        this.name = name;
        this.superClass = superClass;
        this.interfaces.addAll(interfaces);
        this.fields.addAll(fields);
        this.constructors.addAll(constructors);
    }

    public static BeeClass.Builder builder() {
        return new Builder();
    }

    public Version getClassFileVersion() {
        return classFileVersion;
    }

    public BeePackage getPackage() {
        return beePackage;
    }

    public String getName() {
        return name;
    }

    public Set<BeeConstructor> getConstructors() {
        return constructors;
    }

    public Set<ClassAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    public Class<?> getSuperClass() {
        return superClass;
    }

    public Set<BeeField> getFields() {
        return fields;
    }

    public static class Builder {
        private Version version;
        private final Set<ClassAccessFlag> accessFlags = new HashSet<>();
        private final Set<Class<?>> interfaces = new HashSet<>();
        private final Set<BeeField> fields = new HashSet<>();
        private final Set<BeeConstructor> constructors = new HashSet<>();
        private BeePackage beePackage;
        private Class<?> superClass = Object.class;
        private String name;

        private Builder() {
        }

        public Builder withClassFileVersion(Version version){
            this.version=version;
            return this;
        }

        public BeeClass.Builder withPackage(String beePackage) {
            this.beePackage = new BeePackage(beePackage);
            return this;
        }

        public BeeClass.Builder withAccessFlags(ClassAccessFlag... accessFlags) {
            this.accessFlags.addAll(Arrays.asList(accessFlags));
            return this;
        }

        public BeeClass.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSuperClass(Class<?> superClass) {
            this.superClass = superClass;
            return this;
        }

        public Builder withInterfaces(Class<?>... interfaces) {
            this.interfaces.addAll(Arrays.asList(interfaces));
            return this;
        }

        public Builder withFields(BeeField... fields) {
            this.fields.addAll(Arrays.asList(fields));
            return this;
        }

        public Builder withConstructors(BeeConstructor... constructors) {
            this.constructors.addAll(Arrays.asList(constructors));
            return this;
        }

        public BeeClass build() {
            return new BeeClass(version, beePackage, accessFlags, name, superClass, interfaces, fields, constructors);
        }


    }
}

package nl.sander.beejava.api;

import nl.sander.beejava.flags.ClassAccessFlags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BeeSource {
    private final Version classFileVersion;
    private final BeePackage beePackage;
    private final Set<ClassAccessFlags> accessFlags = new HashSet<>();
    private final String simpleName;
    private final Class<?> superClass;
    private final Set<Class<?>> interfaces = new HashSet<>();
    private final Set<BeeField> fields = new HashSet<>();
    private final Set<BeeConstructor> constructors = new HashSet<>();
    private final Set<BeeMethod> methods = new HashSet<>();

    private BeeSource(Version classFileVersion,
                      BeePackage beePackage, Set<ClassAccessFlags> accessFlags, String simpleName, Class<?> superClass,
                      Set<Class<?>> interfaces, Set<BeeField> fields, Set<BeeConstructor> constructors, Set<BeeMethod> methods) {
        this.classFileVersion = classFileVersion;
        this.beePackage = beePackage;
        this.accessFlags.addAll(accessFlags);
        this.simpleName = simpleName;
        this.superClass = superClass;
        this.interfaces.addAll(interfaces);
        this.fields.addAll(fields);
        this.constructors.addAll(constructors);
        this.methods.addAll(methods);
    }

    public static BeeSource.Builder builder() {
        return new Builder();
    }

    public Version getClassFileVersion() {
        return classFileVersion;
    }

    public BeePackage getPackage() {
        return beePackage;
    }

    /**
     * returns the unqualified name, like java.lang.Class
     */
    public String getSimpleName() {
        return simpleName;
    }

    public Set<BeeConstructor> getConstructors() {
        return constructors;
    }

    public Set<BeeMethod> getMethods() {
        return methods;
    }

    public Set<ClassAccessFlags> getAccessFlags() {
        return accessFlags;
    }

    /**
     * returns the full name, like java.lang.Class
     */
    public String getName() {
        return beePackage.getName() + "." + simpleName;
    }

    public Class<?> getSuperClass() {
        return superClass;
    }

    public Set<Class<?>> getInterfaces() {
        return interfaces;
    }

    public Set<BeeField> getFields() {
        return fields;
    }

    public static class Builder {
        private final Set<ClassAccessFlags> accessFlags = new HashSet<>();
        private final Set<Class<?>> interfaces = new HashSet<>();
        private final Set<BeeField> fields = new HashSet<>();
        private Version version;
        private BeePackage beePackage;
        private Class<?> superClass = Object.class;
        private String simpleName;
        private final Set<BeeConstructor> constructors = new HashSet<>();
        private final Set<BeeMethod> methods = new HashSet<>();

        private Builder() {
        }

        public Builder withClassFileVersion(Version version) {
            this.version = version;
            return this;
        }

        public BeeSource.Builder withPackage(String beePackage) {
            this.beePackage = new BeePackage(beePackage);
            return this;
        }

        public BeeSource.Builder withAccessFlags(ClassAccessFlags... accessFlags) {
            this.accessFlags.addAll(Arrays.asList(accessFlags));
            return this;
        }

        public BeeSource.Builder withSimpleName(String simpleName) {
            this.simpleName = simpleName;
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

        public Builder withMethods(BeeMethod... methods) {
            this.methods.addAll(Arrays.asList(methods));
            return this;
        }

        public BeeSource build() {
            BeeSource beeSource = new BeeSource(version, beePackage, accessFlags, simpleName, superClass, interfaces, fields, constructors, methods);
            constructors.forEach(c -> c.setOwner(beeSource));
            methods.forEach(m -> m.setOwner(beeSource));
            return beeSource;
        }

    }
}

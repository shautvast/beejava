package nl.sander.beejava.api;

import nl.sander.beejava.flags.ClassAccessFlags;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains all information needed for compilation
 *
 * End users need to create an instance of this class (using the Builder) and add all fields, methods etc.
 * Once created the BeeSource object is immutable.
 */
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

    /**
     * Create a new BeeSource Builder class.
     *
     * @return a new instance of a Builder
     */
    public static BeeSource.Builder builder() {
        return new Builder();
    }

    /**
     * @return The classfile version
     */
    public Version getClassFileVersion() {
        return classFileVersion;
    }

    /**
     * @return The package in which the compiled class will reside.
     */
    public BeePackage getPackage() {
        return beePackage;
    }

    /**
     * returns the unqualified name, like java.lang.Class
     */
    public String getSimpleName() {
        return simpleName;
    }

    /**
     * @return all constructors that are provided with the class
     */
    public Set<BeeConstructor> getConstructors() {
        return Collections.unmodifiableSet(constructors);
    }

    /**
     * @return all methods that are provided with the class
     */
    public Set<BeeMethod> getMethods() {
        return methods;
    }

    /**
     * @return The access flags for the class
     */
    public Set<ClassAccessFlags> getAccessFlags() {
        return Collections.unmodifiableSet(accessFlags);
    }

    /**
     * @return The full name, like java.lang.Class
     */
    public String getName() {
        return beePackage.getName() + "." + simpleName;
    }

    /**
     * @return The superclass
     */
    public Class<?> getSuperClass() {
        return superClass;
    }

    /**
     * @return a list of unique interfaces that the class will implements
     */
    public Set<Class<?>> getInterfaces() {
        return Collections.unmodifiableSet(interfaces);
    }

    /**
     * @return a list of unique fields that the class will contain
     */
    public Set<BeeField> getFields() {
        return Collections.unmodifiableSet(fields);
    }

    /**
     * Helper class for creating BeeSource classes
     */
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

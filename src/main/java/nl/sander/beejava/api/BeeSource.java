package nl.sander.beejava.api;

import nl.sander.beejava.flags.ClassAccessFlags;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains parsed class elements like constructors and methods, but the opcode is not compiled to bytecode yet.
 */
public final class BeeSource {
    private final Set<ClassAccessFlags> accessFlags = new HashSet<>();
    private final Set<Class<?>> interfaces = new HashSet<>();
    private final Set<BeeField> fields = new HashSet<>();
    private final Set<BeeConstructor> constructors = new HashSet<>();
    private final Set<BeeMethod> methods = new HashSet<>();
    private Version classFileVersion;
    private String name;
    private Class<?> superClass = Object.class;

    /**
     * @return The classfile version
     */
    public Version getClassFileVersion() {
        return classFileVersion;
    }

    public void setClassFileVersion(Version classFileVersion) {
        this.classFileVersion = classFileVersion;
    }

    /**
     * @return all constructors that are provided with the class
     */
    public Set<BeeConstructor> getConstructors() {
        return Collections.unmodifiableSet(constructors);
    }

    public void addConstructors(BeeConstructor... constructors) {
        this.constructors.addAll(Set.of(constructors));
    }

    /**
     * @return all methods that are provided with the class
     */
    public Set<BeeMethod> getMethods() {
        return methods;
    }

    public void addMethods(BeeMethod... methods) {
        this.methods.addAll(Set.of(methods));
    }

    /**
     * @return The access flags for the class
     */
    public Set<ClassAccessFlags> getAccessFlags() {
        return Collections.unmodifiableSet(accessFlags);
    }

    public void addAccessFlags(ClassAccessFlags... classAccessFlags) {
        this.accessFlags.addAll(Set.of(classAccessFlags));
    }

    /**
     * @return The full name, like java.lang.Class
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The superclass
     */
    public Class<?> getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class<?> superClass) {
        this.superClass = superClass;
    }

    /**
     * @return a list of unique interfaces that the class will implements
     */
    public Set<Class<?>> getInterfaces() {
        return Collections.unmodifiableSet(interfaces);
    }

    public void addInterfaces(Class<?>... interfaces) {
        this.interfaces.addAll(Set.of(interfaces));
    }

    /**
     * @return a list of unique fields that the class will contain
     */
    public Set<BeeField> getFields() {
        return Collections.unmodifiableSet(fields);
    }

    public void addFields(BeeField... fields) {
        this.fields.addAll(Set.of(fields));
    }

    public BeeField getField(String fieldName) {
        return fields.stream()
                .filter(f -> f.getName().equals(fieldName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("field " + fieldName + " not found in " + getName()));
    }
}

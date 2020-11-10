package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.constantpool.entry.ClassEntry;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;
import nl.sander.beejava.constantpool.entry.Utf8Entry;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompiledClass {
    private final Set<ConstantPoolEntry> constantTree = new LinkedHashSet<>();
    private final Set<ClassEntry> interfaces = new HashSet<>();
    private final BeeClass beeClass;
    private ClassEntry thisClass;
    private ClassEntry superClass;

    public CompiledClass(BeeClass beeClass) {
        this.beeClass = beeClass;
    }

    public int getSuperIndex() {
        return superClass.getIndex();
    }

    public int geThisIndex() {
        return thisClass.getIndex();
    }

    public ClassEntry addSuperClass() {
        if (superClass == null) {
            superClass = createClassEntry(beeClass.getSuperClass().getName());
        }
        return superClass;
    }

    public ClassEntry addThisClass() {
        if (thisClass == null) {
            thisClass = createClassEntry(internalName(beeClass.getName()));
        }
        return thisClass;
    }

    public Set<ConstantPoolEntry> getConstantTree() {
        return constantTree;
    }

    public Set<ClassEntry> getInterfaces() {
        return interfaces;
    }

    public void addInterface(ClassEntry interfaceEntry) {
        interfaces.add(interfaceEntry);
    }

    public void addConstantPoolEntry(ConstantPoolEntry interfaceEntry) {
        constantTree.add(interfaceEntry);
    }

    public void addInterfaces() {
        beeClass.getInterfaces().forEach(interfase -> {
            ClassEntry interfaceEntry = new ClassEntry(new Utf8Entry(internalName(interfase.getName())));
            addInterface(interfaceEntry);
            addConstantPoolEntry(interfaceEntry);
        });
    }

    public void addFields() {
        beeClass.getFields().forEach(field -> {
            // TODO
        });
    }

    private ClassEntry createClassEntry(String name) {
        return new ClassEntry(new Utf8Entry(internalName(name)));
    }

    private String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }
}

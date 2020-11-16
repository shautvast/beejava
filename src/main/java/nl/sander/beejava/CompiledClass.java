package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;
import nl.sander.beejava.classinfo.FieldInfo;
import nl.sander.beejava.classinfo.MethodInfo;
import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ClassEntry;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompiledClass {
    private final Set<ConstantPoolEntry> constantTree = new LinkedHashSet<>();
    private final Set<ClassEntry> interfaces = new HashSet<>();
    private final Set<FieldInfo> fields = new HashSet<>();
    private final Set<MethodInfo> methods = new HashSet<>();
    private final BeeSource beeSource;
    private ClassEntry thisClass;
    private ClassEntry superClass;
    private ConstantPool constantPool;

    CompiledClass(BeeSource beeSource) {
        this.beeSource = beeSource;
    }

    int getSuperIndex() {
        return superClass.getIndex();
    }

    int geThisIndex() {
        return thisClass.getIndex();
    }

    void setSuperClass(ClassEntry newSuperClass) {
        if (superClass == null) {
            superClass = newSuperClass;
        }
    }

    Set<ConstantPoolEntry> getConstantTree() {
        return constantTree;
    }

    Set<ClassEntry> getInterfaces() {
        return interfaces;
    }

    void addConstantPoolEntry(ConstantPoolEntry entry) {
        constantTree.add(entry); // no duplicates
    }

    void addInterface(ClassEntry interfaceEntry) {
        interfaces.add(interfaceEntry);
    }

    BeeSource getSource() {
        return beeSource;
    }

    public void setThisClass(ClassEntry newThisClass) {
        if (thisClass == null) {
            thisClass = newThisClass;
        }
    }

    public void addField(FieldInfo fieldInfo) {
        fields.add(fieldInfo);
    }

    public Set<FieldInfo> getFields() {
        return fields;
    }

    public void addMethod(MethodInfo methodInfo) {
        methods.add(methodInfo);
    }

    public Set<MethodInfo> getMethods() {
        return methods;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }
}

package nl.sander.bejava;

import nl.sander.bejava.api.BeSource;
import nl.sander.bejava.classinfo.FieldInfo;
import nl.sander.bejava.classinfo.MethodInfo;
import nl.sander.bejava.constantpool.ConstantPool;
import nl.sander.bejava.constantpool.entry.ClassEntry;
import nl.sander.bejava.constantpool.entry.ConstantPoolEntry;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Eventually contains all information for generation of the actual bytecode.
 * Output of the {@link Compiler} class.
 */
public class CompiledClass {
    private final Set<ConstantPoolEntry> constantTree = new LinkedHashSet<>();
    private final Set<ClassEntry> interfaces = new HashSet<>();
    private final Set<FieldInfo> fields = new HashSet<>();
    private final Set<MethodInfo> methods = new HashSet<>();
    private final BeSource beSource;
    private ClassEntry thisClass;
    private ClassEntry superClass;
    private ConstantPool constantPool;

    CompiledClass(BeSource beSource) {
        this.beSource = beSource;
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

    public Set<ConstantPoolEntry> getConstantTree() {
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

    BeSource getSource() {
        return beSource;
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

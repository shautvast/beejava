package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.constantpool.entry.ClassEntry;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

class CompiledClass {
    private final Set<ConstantPoolEntry> constantTree = new LinkedHashSet<>();
    private final Set<ClassEntry> interfaces = new HashSet<>();
    private final BeeClass beeClass;
    private ClassEntry thisClass;
    private ClassEntry superClass;

    CompiledClass(BeeClass beeClass) {
        this.beeClass = beeClass;
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

    BeeClass getBeeClass() {
        return beeClass;
    }

    ClassEntry getThisClass() {
        return thisClass;
    }

    public void setThisClass(ClassEntry newThisClass) {
        if (thisClass == null) {
            thisClass = newThisClass;
        }
    }
}

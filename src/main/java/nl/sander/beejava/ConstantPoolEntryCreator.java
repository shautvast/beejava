package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.constantpool.entry.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Responsible for creating unique constant pool entries
 */
class ConstantPoolEntryCreator {
    private final Map<Integer, ConstantPoolEntry> cache = new HashMap<>();
    private final CompiledClass compiledClass;

    public ConstantPoolEntryCreator(CompiledClass compiledClass) {
        this.compiledClass = compiledClass;
    }

    FieldRefEntry getOrCreateFieldRefEntry(CodeLine codeLine) {
        return cache(new FieldRefEntry(getOrCreateClassEntry(codeLine), createFieldNameAndType(codeLine)));
    }

    MethodRefEntry getOrCreateMethodRefEntry(CodeLine codeline) {
        ClassEntry classEntry = getOrCreateClassEntry(codeline);
        NameAndTypeEntry nameAndType = getOrCreateMethodNameAndType(codeline);
        return cache(new MethodRefEntry(classEntry, nameAndType));
    }

    private NameAndTypeEntry createFieldNameAndType(CodeLine codeline) {
        return cache(new NameAndTypeEntry(cache(new Utf8Entry(codeline.getField().getName())), cache(new Utf8Entry(TypeMapper.map(codeline.getField().getType())))));
    }

    private NameAndTypeEntry getOrCreateMethodNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(
                cache(new Utf8Entry(codeline.getMethodName())),
                cache(new Utf8Entry(codeline.getMethodSignature())));
    }

    private ClassEntry getOrCreateClassEntry(CodeLine codeline) {
        if (codeline.getRef() == Ref.SUPER) { //this and super are rather special
            ClassEntry superClass = getClassEntry(compiledClass.getBeeClass().getSuperClass().getName());
            compiledClass.setSuperClass(superClass);
            return superClass;

        } else if (codeline.getRef() == Ref.THIS) {
            addThisClass();
            return compiledClass.getThisClass();
        }
        //TODO other cases
        throw new RuntimeException("shouldn't be here");
    }

    void addThisClass(){
        ClassEntry classEntry = getClassEntry(compiledClass.getBeeClass().getName());
        compiledClass.setThisClass(classEntry);
    }

    private ClassEntry getClassEntry(String externalClassName) {
        return cache(new ClassEntry(cache(new Utf8Entry(internalName(externalClassName)))));
    }

    public void addInterfaces() {
        compiledClass.getBeeClass().getInterfaces().forEach(interfase -> {
            ClassEntry interfaceEntry = cache(new ClassEntry(cache(new Utf8Entry(internalName(interfase.getName())))));
            compiledClass.addInterface(interfaceEntry);
            compiledClass.addConstantPoolEntry(interfaceEntry);
        });
    }



    public void addFields() {
        compiledClass.getBeeClass().getFields().forEach(field -> {
            // TODO
        });
    }

    private String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }

    @SuppressWarnings("unchecked")
    <T extends ConstantPoolEntry> T cache(T newEntry) {
        // First create an object using the supplier, but if it's found in cache, return the cached entry and discard the first.
        // Can't check for equality unless you create a potential new entry first
        int hash = newEntry.hashCode();
        return (T) cache.computeIfAbsent(hash, k -> newEntry);
        // a hashmap with key hash of value is weird right?
        // A HashSet is a HashMap with entry: key = value, which would work, but I cannot _get_ anything from a set.
    }

}

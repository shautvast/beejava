package nl.sander.beejava;

import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.classinfo.FieldInfo;
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
        if (codeline.hasRefToOwnField()) {
            return cache(new NameAndTypeEntry(
                    cache(new Utf8Entry(codeline.getOwnfield().getName())),
                    cache(new Utf8Entry(TypeMapper.map(codeline.getOwnfield().getType()))))); // is actually a shortcut
        } else {
            return cache(new NameAndTypeEntry(
                    cache(new Utf8Entry(codeline.getExternalfield())),
                    cache(new Utf8Entry("L" + codeline.getExternalfieldClass()))
            ));
        }
    }

    private NameAndTypeEntry getOrCreateMethodNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(
                cache(new Utf8Entry(codeline.getMethodName())),
                cache(new Utf8Entry(codeline.getMethodSignature())));
    }

    private ClassEntry getOrCreateClassEntry(CodeLine codeline) {
        if (codeline.hasRef()) {
            if (codeline.getRef() == Ref.SUPER) { //this and super are rather special
                ClassEntry superClass = getClassEntry(compiledClass.getSource().getSuperClass().getName());
                compiledClass.setSuperClass(superClass);
                return superClass;

            } else if (codeline.getRef() == Ref.THIS) {
                return addThisClass();
            }
        } else if (codeline.hasClassName()) {
            return getClassEntry(codeline.getClassName());
        }

        throw new RuntimeException("shouldn't be here");
    }

    ClassEntry addThisClass() {
        ClassEntry classEntry = getClassEntry(compiledClass.getSource().getName());
        compiledClass.addConstantPoolEntry(classEntry);
        return classEntry;
    }

    private ClassEntry getClassEntry(String externalClassName) {
        return cache(new ClassEntry(cache(new Utf8Entry(internalName(externalClassName)))));
    }

    /*
     * Adds interfaces to the constant pool as well as the class.
     *
     * interfaces[] in the class file is an array of cp entries
     */
    public void addInterfaces() {
        compiledClass.getSource().getInterfaces().forEach(interfase -> {
            ClassEntry interfaceEntry = cache(new ClassEntry(cache(new Utf8Entry(internalName(interfase.getName())))));
            compiledClass.addInterface(interfaceEntry);
            compiledClass.addConstantPoolEntry(interfaceEntry);
        });
    }

    /*
     * If a constant is in the codeline, it needs to be added to the constant pool.
     */
    public ConstantPoolEntry getOrCreatePrimitiveEntry(CodeLine codeline) {
        Object v = codeline.getConstValue();

        if (v instanceof String) {
            return cache(new StringEntry(cache(new Utf8Entry((String) v))));
        } else if (v instanceof Integer) {
            return cache(new IntegerEntry((Integer) v));
        } else if (v instanceof Long) {
            return cache(new LongEntry((Long) v));
        } else if (v instanceof Float) {
            return cache(new FloatEntry((Float) v));
        } else if (v instanceof Double) {
            return cache(new DoubleEntry((Double) v));
        }
        throw new RuntimeException(); // TODO find out why are you here
    }

    public void addFields() {
        compiledClass.getSource().getFields()
                .forEach(f -> {
                    Utf8Entry name = cache(new Utf8Entry(f.getName()));
                    Utf8Entry descriptor = cache(new Utf8Entry(internalName(f.getType().getName())));
                    compiledClass.addField(new FieldInfo(name, descriptor).addAccessFlags(f.getAccessFlags()));
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

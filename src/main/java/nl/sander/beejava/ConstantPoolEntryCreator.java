package nl.sander.beejava;

import nl.sander.beejava.api.CodeLine;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.classinfo.FieldInfo;
import nl.sander.beejava.classinfo.attributes.CodeAttribute;
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

    /*
     * creates a FieldRefEntry when not found in cache, otherwise gets it from there
     */
    FieldRefEntry getOrCreateFieldRefEntry(CodeLine codeLine) {
        return cache(new FieldRefEntry(getOrCreateClassEntry(codeLine), getOrCreateFieldNameAndType(codeLine)));
    }

    /*
     * creates a MethodRefEntry when not found in cache, otherwise gets it from there
     */
    MethodRefEntry getOrCreateMethodRefEntry(CodeLine codeline) {
        ClassEntry classEntry = getOrCreateClassEntry(codeline);
        return cache(new MethodRefEntry(classEntry, getOrCreateMethodNameAndType(codeline)));
    }

    /*
     * creates a NamAndTypeEntry for a field when not found in cache, otherwise gets it from there
     */
    private NameAndTypeEntry getOrCreateFieldNameAndType(CodeLine codeline) {
        if (codeline.hasRefToOwnField()) {
            return cache(new NameAndTypeEntry(
                    cache(new Utf8Entry(codeline.getOwnfield().getName())),
                    cache(new Utf8Entry(TypeMapper.map(codeline.getOwnfield().getType()))))); // is actually a shortcut
        } else {//TODO this method may need some work
            return cache(new NameAndTypeEntry(
                    cache(new Utf8Entry(codeline.getExternalfield().getName())),
                    cache(new Utf8Entry(TypeMapper.map(codeline.getExternalfield().getType())))
            ));
        }
    }

    /*
     * creates a NamAndTypeEntry for a method when not found in cache, otherwise gets it from there
     */
    private NameAndTypeEntry getOrCreateMethodNameAndType(CodeLine codeline) {
        return new NameAndTypeEntry(
                cache(new Utf8Entry(codeline.getMethodName())),
                cache(new Utf8Entry(codeline.getMethodSignature())));
    }

    /*
     * creates a ClassEntry when not found in cache, otherwise gets it from there
     */
    private ClassEntry getOrCreateClassEntry(CodeLine codeline) {
        if (codeline.hasRef()) {
            if (codeline.getRef() == Ref.SUPER) { // this and super are rather special
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

    /*
     * this method gives me a headache. It adds, but also creates a classEntry for the this class
     */
    ClassEntry addThisClass() {
        ClassEntry classEntry = getClassEntry(compiledClass.getSource().getName());
        compiledClass.addConstantPoolEntry(classEntry);
        return classEntry;
    }

    /*
     * get or create a ClassEntry
     */
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

    /*
     * maps a field from the source to a FieldInfo and adds that to the CompiledClass.
     */
    public void addFields() {
        compiledClass.getSource().getFields().stream()
                .map(field -> new FieldInfo(
                                cache(new Utf8Entry(field.getName())),
                                cache(new Utf8Entry(internalName(field.getType().getName())))
                        ).addAccessFlags(field.getAccessFlags())
                )
                .forEach(compiledClass::addField);
    }

    public Utf8Entry getOrCreateUtf8(String utf8) {
        return cache(new Utf8Entry(utf8));
    }

    // why not put this everywhere, it's not like it's ever going to change
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

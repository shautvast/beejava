package nl.sander.beejava;

import nl.sander.beejava.constantpool.entry.*;
import nl.sander.beejava.operands.ConstantOperand;

import java.util.HashMap;
import java.util.Map;


/**
 * Responsible for creating unique constant pool entries
 */
class ConstantPoolEntryCreator {

    private static final Map<Integer, ConstantPoolEntry> cache = new HashMap<>();

    private ConstantPoolEntryCreator() {
    }

    /*
     * creates a FieldRefEntry when not found in cache, otherwise gets it from there
     */
    static FieldRefEntry getOrCreateFieldRefEntry(String declaringClassName, String fieldName, Class<?> fieldType) {
        return cache(new FieldRefEntry(getOrCreateClassEntry(declaringClassName), getOrCreateFieldNameAndType(fieldName, fieldType)));
    }

    /*
     * creates a MethodRefEntry when not found in cache, otherwise gets it from there
     */
    static MethodRefEntry getOrCreateMethodRefEntry(String className, String methodName, String signature) {
        ClassEntry classEntry = getOrCreateClassEntry(className);
        return cache(new MethodRefEntry(classEntry, getOrCreateMethodNameAndType(methodName, signature)));
    }

    /*
     * creates a NamAndTypeEntry for a field when not found in cache, otherwise gets it from there
     */
    private static NameAndTypeEntry getOrCreateFieldNameAndType(String name, Class<?> type) {
        return cache(new NameAndTypeEntry(
                cache(new Utf8Entry(name)),
                cache(new Utf8Entry(TypeMapper.map(type)))));
    }

    /*
     * creates a NamAndTypeEntry for a method when not found in cache, otherwise gets it from there
     */
    private static NameAndTypeEntry getOrCreateMethodNameAndType(String methodName, String methodSignature) {
        return new NameAndTypeEntry(
                cache(new Utf8Entry(methodName)),
                cache(new Utf8Entry(methodSignature)));
    }

    /*
     * get or create a ClassEntry
     */
    public static ClassEntry getOrCreateClassEntry(String externalClassName) {
        return cache(new ClassEntry(cache(new Utf8Entry(internalName(externalClassName)))));
    }

    public static ClassEntry getOrCreateClassEntry(Class<?> type) {
        return getOrCreateClassEntry(type.getName());
    }

    /*
     * If a constant is in the codeline, it needs to be added to the constant pool.
     */
    public static ConstantPoolEntry getOrCreatePrimitiveConstantEntry(ConstantOperand operand) {
        return cache(switch (operand.getType()) {
            case STRING -> new StringEntry(cache(new Utf8Entry(operand.getValue())));
            case INT, BYTE, SHORT -> new IntegerEntry(operand.getValue());
            case LONG -> new LongEntry(Long.parseLong(operand.getValue()));
            case FLOAT -> new FloatEntry(operand.getValue());
            case DOUBLE -> new DoubleEntry(Double.parseDouble(operand.getValue()));
            case CHAR -> new IntegerEntry(Character.codePointAt(operand.getValue(), 0));
            case BOOLEAN -> new IntegerEntry(Boolean.parseBoolean(operand.getValue()) ? 1 : 0);
        });
    }

    public static Utf8Entry getOrCreateUtf8(String utf8) {
        return cache(new Utf8Entry(utf8));
    }

    // why not put this everywhere, it's not like it's ever going to change
    private static String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }

    @SuppressWarnings("unchecked")
    static <T extends ConstantPoolEntry> T cache(T newEntry) {
        // if the argument newEntry is found in cache, return the cached entry and discard the argument
        // Otherwise store it.
        // Can't check for equality unless you create a potential new entry first
        int hash = newEntry.hashCode();
        return (T) cache.computeIfAbsent(hash, k -> newEntry);
        // a hashmap with key hash of value is weird right?
        // A HashSet is a HashMap with entry: key = value, which would work, but I cannot _get_ anything from a set.
    }

}

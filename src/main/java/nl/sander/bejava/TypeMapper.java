package nl.sander.bejava;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TypeMapper {
    private static final Map<Class<?>, String> MAP = new ConcurrentHashMap<>();

    static {
        MAP.put(byte.class, "B");
        MAP.put(char.class, "C");
        MAP.put(double.class, "D");
        MAP.put(float.class, "F");
        MAP.put(int.class, "I");
        MAP.put(long.class, "J");
        MAP.put(short.class, "S");
        MAP.put(boolean.class, "Z");
        MAP.put(void.class, "V");
    }

    //TODO something with arrays
    public static String map(Class<?> type) {
        return Optional.ofNullable(MAP.get(type)).orElseGet(() -> {
            if (type.isArray()) {
                return internalName(type.getName());
            } else {
                return 'L' + internalName(type.getName() + ';');
            }
        });
    }

    private static String internalName(String name) {
        return name.replaceAll("\\.", "/");
    }
}

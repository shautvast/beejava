package nl.sander.beejava;

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
    }

    public static String map(Class<?> type) {
        return Optional.ofNullable(MAP.get(type))
                .orElseThrow(() -> new RuntimeException("Type " + type.getName() + " not found")); // this MUST not happen -> TODO map all types
    }


}

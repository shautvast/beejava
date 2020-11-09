package nl.sander.beejava;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TypeMapper {
    private static final Map<Class<?>, String> MAP = new ConcurrentHashMap<>();

    static {
        MAP.put(int.class, "I");
    }

    public static String map(Class<?> type) {
        return Optional.ofNullable(MAP.get(type))
                .orElseThrow(() -> new RuntimeException("Type " + type.getName() + " not found")); //this MUST not happen -> TODO map all types
    }


}

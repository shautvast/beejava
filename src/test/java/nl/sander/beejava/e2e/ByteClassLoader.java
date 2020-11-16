package nl.sander.beejava.e2e;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ByteClassLoader extends ClassLoader {

    private final ConcurrentMap<String, byte[]> classByteCode = new ConcurrentHashMap<>();

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytecode = Optional.ofNullable(classByteCode.get(name)).orElseThrow(() -> new ClassNotFoundException(name));
        return defineClass(name, bytecode, 0, bytecode.length);
    }


    public void setByteCode(String className, byte[] bytecode) {
        classByteCode.putIfAbsent(className, bytecode);
    }
}

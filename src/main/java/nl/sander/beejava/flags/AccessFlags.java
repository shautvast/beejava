package nl.sander.beejava.flags;

import java.util.Collection;

public interface AccessFlags {
    static int combine(Collection<? extends AccessFlags> flags) {
        return flags.stream().mapToInt(AccessFlags::getBytecode).reduce(0, (result, value) -> result | value);
    }

    int getBytecode();

}

package nl.sander.beejava.flags;

import java.util.Set;

public interface AccessFlag {

    int getBytecode();

    default int getCombineBytecode(Set<AccessFlag> accessflags) {
        return accessflags.stream().mapToInt(AccessFlag::getBytecode).sum();
    }
}

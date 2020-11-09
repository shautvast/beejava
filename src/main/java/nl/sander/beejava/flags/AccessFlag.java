package nl.sander.beejava.flags;

import java.util.Set;

// TODO all access flags can be moved together
public interface AccessFlag {

    int getBytecode();

    default int getCombineBytecode(Set<AccessFlag> accessflags) {
        return accessflags.stream().mapToInt(AccessFlag::getBytecode).sum();
    }
}

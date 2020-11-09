package nl.sander.beejava.constantpool.entry;

import java.util.Collections;
import java.util.Set;

/**
 * Is a constant without children
 */
public abstract class LeafConstant extends NodeConstant {
    @Override
    public Set<NodeConstant> getChildren() {
        return Collections.emptySet();
    }
}

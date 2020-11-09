package nl.sander.beejava;

import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.NodeConstant;

import java.util.Set;

/**
 * Transforms the hierachical constant tree into a flat datastructure. The walks the tree adding indexes to each element.
 */
public class ConstantPoolCreator {
    private ConstantPool constantPool; // the constant pool that is being created
    private int index; // the current index that will be assigned to a constant pool entry. It needs to be unique for each entry.
    // References to other elements in the pool are made through indexes, so they have to be valid to guarantee that the class can be loaded by the JVM.

    public ConstantPool createConstantPool(Set<NodeConstant> constantTree) {
        constantPool = new ConstantPool();
        constantPool.add(null); // dummy element to align it's index with the indexes in the elements themselves
        index = 0;
        updateToplevelElements(constantTree);
        return constantPool;
    }

    private void updateToplevelElements(Set<NodeConstant> children) {
        for (NodeConstant child : children) {
            addToPool(child);
            updateChildElements(child.getChildren());
            // first the complete toplevel element including it's children, then next toplevel element
        }
    }

    private void updateChildElements(Set<NodeConstant> children) {
        // first all direct children
        for (NodeConstant child : children) {
            addToPool(child);
        }
        // then further lineage
        for (NodeConstant child : children) {
            updateChildElements(child.getChildren());
        }
    }

    private void addToPool(NodeConstant entry) {
        index += 1;
        entry.setIndex(index);
        constantPool.add(entry);
    }
}

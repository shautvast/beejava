package nl.sander.beejava;

import nl.sander.beejava.constantpool.ConstantPool;
import nl.sander.beejava.constantpool.entry.ConstantPoolEntry;

import java.util.Set;

/**
 * Transforms the hierachical constant tree into a flat datastructure. The walks the tree adding indexes to each element.
 */
public class ConstantPoolCreator {
    private ConstantPool constantPool; // the constant pool that is being created
    private int index; // the current index that will be assigned to a constant pool entry. It needs to be unique for each entry.
    // References to other elements in the pool are made through indexes, so they have to be valid to guarantee that the class can be loaded by the JVM.

    public ConstantPool createConstantPool(Set<ConstantPoolEntry> constantTree) {
        constantPool = new ConstantPool();
        index = 0;
        updateToplevelElements(constantTree);
        return constantPool;
    }

    private void updateToplevelElements(Set<ConstantPoolEntry> children) {
        for (ConstantPoolEntry child : children) {
            addToPool(child);
            updateChildElements(child.getChildren());
            // first the complete toplevel element including it's children, then next toplevel element
        }
    }

    private void updateChildElements(Set<ConstantPoolEntry> children) {
        // first all direct children
        for (ConstantPoolEntry child : children) {
            addToPool(child);
        }
        // then further lineage
        for (ConstantPoolEntry child : children) {
            updateChildElements(child.getChildren());
        }
    }

    private void addToPool(ConstantPoolEntry entry) {
        index += 1;
        entry.setIndex(index);
        constantPool.add(entry);
    }
}

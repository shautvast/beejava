package nl.sander.bejava;

import nl.sander.bejava.constantpool.ConstantPool;
import nl.sander.bejava.constantpool.entry.ConstantPoolEntry;

import java.util.Set;

/**
 * Transforms the hierachical constant tree into a flat datastructure. The walks the tree adding indexes to each element.
 */
public class ConstantPoolCreator {
    private ConstantPool constantPool; // the constant pool that is being created
    private int index; // the current index that will be assigned to a constant pool entry. It needs to be unique for each entry.
    // References to other elements in the pool are made through indexes, so they have to be valid to guarantee that the class can be loaded by the JVM.

    public static ConstantPool create(Set<ConstantPoolEntry> constantTree){
        return new ConstantPoolCreator().createConstantPool(constantTree);
    }

    /**
     * Creates a ConstantPool from the tree structure provided by the {@link Compiler}
     *
     * @param constantTree a Set of CP entries assumed to be laid out correctly
     * @return a ConstantPool, a list of entries
     */
    /*
     * resets the current state to create a new constant pool
     *
     * This method is halfway at memory efficiency. Next step could be to always have just 1 ConstantPool.
     * Downsides: no parallelism, maybe keep more track of lingering references
     */
    //@NotThreadSafe
    public ConstantPool createConstantPool(Set<ConstantPoolEntry> constantTree) {
        constantPool = new ConstantPool();
        index = 0;
        updateToplevelElements(constantTree);
        return constantPool;
    }

    /*
     * javac:
     * - start with toplevel element (like MethodRef) -> grandparents
     * - then add direct children -> the parents
     * - then add grandchildren (I don't think there's more levels)
     */
    private void updateToplevelElements(Set<ConstantPoolEntry> children) {
        for (var topElement : children) {
            addToPool(topElement); // grandparents
            updateChildElements(topElement.getChildren());
        }
    }

    private void updateChildElements(Set<ConstantPoolEntry> children) {
        // parents
        for (var child : children) {
            addToPool(child);
        }
        // then grandchildren
        for (var child : children) {
            updateChildElements(child.getChildren()); // no problem if there are great grand children
        }
    }

    /*
     * This just adds the next index to an entry and adds the entry to a flat structure.
     * The tree structure makes sure all references by index will be correct
     */
    private void addToPool(ConstantPoolEntry entry) {
        index += 1;
        entry.setIndex(index);
        constantPool.add(entry);
    }
}

package nl.sander.beejava.flags;

public enum ClassAccessFlags implements AccessFlags {
    PUBLIC(0x0001), // Declared public; may be accessed from outside its package.
    FINAL(0x0010), // Declared final; no subclasses allowed.
    SUPER(0x0020), // Treat superclass methods specially when invoked by the invokespecial instruction.
    INTERFACE(0x0200), // Is an interface, not a class.
    ABSTRACT(0x0400), // Declared abstract; must not be instantiated.
    SYNTHETIC(0x1000), // Declared synthetic; not present in the source code.
    ANNOTATION(0x2000), // Declared as an annotation type.
    ENUM(0x4000), // Declared as an enum type.
    MODULE(0x8000); // Is a module, not a class or interface.


    private final int bytecode;

    ClassAccessFlags(int bytecode) {
        this.bytecode = bytecode;
    }



    @Override
    public int getBytecode() {
        return bytecode;
    }
}

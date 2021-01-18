package nl.sander.bejava.flags;

import java.util.Optional;

public enum FieldAccessFlag implements AccessFlags {
    PUBLIC(0x0001), // Declared public; may be accessed from outside itspackage.
    PRIVATE(0x0002), // Declared   private;   accessible   only   within   the defining class and other classes belonging to the samenest (§5.4.4).
    PROTECTED(0x0004), // Declared   protected;   may   be   accessed within subclasses.
    STATIC(0x0008), // Declared static.
    FINAL(0x0010), // Declared  final;  never  directly  assigned  to  afterobject construction (JLS §17.5).
    VOLATILE(0x0040), // Declared volatile; cannot be cached.
    ACC_TRANSIENT(0x0080), // Declared  transient;  not  written  or  read  by  apersistent object manager.
    SYNTHETIC(0x1000), // Declared synthetic; not present in the source code.
    ENUM(0x4000); //Declared as an element of an enum.

    private final int bytecode;

    FieldAccessFlag(int bytecode) {
        this.bytecode = bytecode;
    }

    public int getBytecode() {
        return bytecode;
    }

    public static Optional<FieldAccessFlag> get(String text) {
        for (FieldAccessFlag flag : FieldAccessFlag.values()) {
            if (flag.toString().equals(text)) {
                return Optional.of(flag);
            }
        }
        return Optional.empty();
    }
}

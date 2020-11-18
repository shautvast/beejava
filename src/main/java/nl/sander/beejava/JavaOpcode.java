package nl.sander.beejava;

public enum JavaOpcode {
    LDC(0x12, false, +1),
    LDC_W(0x13,true, +1),
    LDC2_W ( 0x14, true, +2),

    ALOAD_0 ( 0x2a, false, +1),
    RETURN ( 0xb1,false, 0),
    GETSTATIC ( 0xb2,true, +1),
    GETFIELD ( 0xb4,true, +1),

    INVOKEINTERFACE ( 0xb5, true, -1),
    INVOKEVIRTUAL ( 0xb6, true, -1),
    INVOKESPECIAL ( 0xb7, true, -1),
    INVOKESTATIC ( 0xb8, true, -1),
    INVOKEDYNAMIC ( 0xba,true,-1);

    private final int opcode;
    private final boolean wide;
    private final int stackDif;

    JavaOpcode(int opcode, boolean wide, int stackDif) {
        this.opcode=opcode;
        this.wide=wide;
        this.stackDif=stackDif;
    }

    public int getByteCode() {
        return opcode;
    }

    public boolean isWide() {
        return wide;
    }

    public int getStackDif() {
        return stackDif;
    }
}

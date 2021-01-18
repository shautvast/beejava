package nl.sander.bejava;

import nl.sander.bejava.testutils.ByteWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * meant as regression tests
 */
public class BytecodeGeneratorTests {
    @Test
    public void testSuperConstructorCall() {
        byte[] bytecode = BytecodeGenerator.generate(Compiler.compile(SourceCompiler.compile("""
                class public nl.sander.beejava.test.EmptyBean
                constructor public()
                  INVOKE this.super()
                  RETURN
                """)));
        assertEquals("""
                        cafe babe 0000 0034 0008 0100 063c 696e\s
                        6974 3e01 0003 2829 5607 0004 0100 206e\s
                        6c2f 7361 6e64 6572 2f62 6565 6a61 7661\s
                        2f74 6573 742f 456d 7074 7942 6561 6e07\s
                        0006 0100 106a 6176 612f 6c61 6e67 2f4f\s
                        626a 6563 7401 0004 436f 6465 0021 0003\s
                        0005 0000 0000 0001 0001 0001 0002 0001\s
                        0007 0000 000c 0000 0001 0000 0000 0000\s
                        0000 0000\s""",
                ByteWriter.printBytes(bytecode));

    }

    @Test
    public void testInterfaceImplementor() {
        byte[] bytecode = BytecodeGenerator.generate(Compiler.compile(SourceCompiler.compile("""
                class public nl.sander.beejava.test.EmptyBean implements java.io.Serializable
                constructor public()
                  INVOKE this.super()V
                  RETURN
                """)));
        assertEquals("""
                        cafe babe 0000 0034 0008 0100 063c 696e\s
                        6974 3e01 0003 2829 5607 0004 0100 0670\s
                        7562 6c69 6307 0006 0100 106a 6176 612f\s
                        6c61 6e67 2f4f 626a 6563 7401 0004 436f\s
                        6465 0020 0003 0005 0000 0000 0001 0001\s
                        0001 0002 0001 0007 0000 000c 0000 0001\s
                        0000 0000 0000 0000 0000\s""",
                ByteWriter.printBytes(bytecode));
    }

    @Test
    public void testPutArgumentInField() {
        byte[] bytecode = BytecodeGenerator.generate(Compiler.compile(SourceCompiler.compile("""           
                class com.acme.SimpleBean(V15)
                field private int field
                constructor public(int arg)
                  INVOKE this.super()V
                  LOAD arg
                  PUT this.field
                  RETURN
                """)));
        assertEquals("""
                        cafe babe 0000 003b 000e 0900 0900 0307\s
                        000a 0c00 0500 0601 0013 636f 6d2f 6163\s
                        6d65 2f53 696d 706c 6542 6561 6e01 0005\s
                        6669 656c 6401 0001 4901 0006 3c69 6e69\s
                        743e 0100 0428 4929 5607 000a 0100 1363\s
                        6f6d 2f61 636d 652f 5369 6d70 6c65 4265\s
                        616e 0700 0c01 0010 6a61 7661 2f6c 616e\s
                        672f 4f62 6a65 6374 0100 0443 6f64 6500\s
                        2000 0900 0b00 0000 0100 0200 0500 0000\s
                        0000 0100 0100 0700 0800 0100 0d00 0000\s
                        0c00 0000 0200 0000 0000 0000 0000 00""",
                ByteWriter.printBytes(bytecode));
    }


}

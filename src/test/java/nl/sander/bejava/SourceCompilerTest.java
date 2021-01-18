package nl.sander.bejava;

import nl.sander.bejava.api.BeField;
import nl.sander.bejava.api.BeMethod;
import nl.sander.bejava.api.BeParameter;
import nl.sander.bejava.api.BeSource;
import nl.sander.bejava.flags.AccessFlags;
import nl.sander.bejava.flags.ClassAccessFlags;
import nl.sander.bejava.flags.FieldAccessFlag;
import nl.sander.bejava.flags.MethodAccessFlag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Compilation from sourcefile to BeeSource is the first pass in the total compilation.
 * A BeeSource object can also be created programmatically.
 */
public class SourceCompilerTest {

    @Test
    public void testCompileSourceFileToSourceObject() {
        BeSource beSource = new SourceCompiler("""           
            class public com.acme.SimpleBean(V15)
            field private int value
            constructor public()
              INVOKE this.super()
              RETURN
            method public getValue() -> int
              RETURN this.value
            method public setValue(int newValue)
              LOAD newValue
              PUT this.value
              RETURN
            """).doCompile();

        assertEquals("com.acme.SimpleBean", beSource.getName());
        assertEquals(ClassAccessFlags.SUPER.getBytecode() | ClassAccessFlags.PUBLIC.getBytecode(), AccessFlags.combine(beSource.getAccessFlags()));
        assertTrue(beSource.getFields().contains(new BeField(beSource.getName(), Set.of(FieldAccessFlag.PRIVATE), int.class, "value")));
        assertEquals(1, beSource.getConstructors().size());
        Set<BeMethod> methods = beSource.getMethods();
        assertEquals(2, methods.size());
        assertTrue(methods.contains(new BeMethod(beSource, "getValue", Set.of(MethodAccessFlag.PUBLIC), Set.of(), int.class, List.of())));
        assertTrue(methods.contains(new BeMethod(beSource, "setValue", Set.of(MethodAccessFlag.PUBLIC), Set.of(new BeParameter(int.class, "newValue")), Void.TYPE, List.of())));
    }
}

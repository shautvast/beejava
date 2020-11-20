package nl.sander.beejava;

import nl.sander.beejava.apiv2.*;
import nl.sander.beejava.flags.AccessFlags;
import nl.sander.beejava.flags.ClassAccessFlags;
import nl.sander.beejava.flags.FieldAccessFlag;
import nl.sander.beejava.flags.MethodAccessFlag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SourceCompilerTest {

    @Test
    public void test() {
        BeeSource beeSource = new SourceCompiler(TestData2.simpleBean).doCompile();
        assertEquals("com.acme.SimpleBean", beeSource.getName());
        assertEquals(ClassAccessFlags.SUPER.getBytecode() | ClassAccessFlags.PUBLIC.getBytecode(), AccessFlags.combine(beeSource.getAccessFlags()));
        assertTrue(beeSource.getFields().contains(new BeeField(Set.of(FieldAccessFlag.PRIVATE), int.class, "value")));
        assertEquals(1, beeSource.getConstructors().size());
        Set<BeeMethod> methods = beeSource.getMethods();
        assertEquals(2, methods.size());
        assertTrue(methods.contains(new BeeMethod("getValue", Set.of(MethodAccessFlag.PUBLIC), Set.of(), int.class, List.of())));
        assertTrue(methods.contains(new BeeMethod("setValue", Set.of(MethodAccessFlag.PUBLIC), Set.of(new BeeParameter(int.class, "newValue")), Void.TYPE, List.of())));
    }
}

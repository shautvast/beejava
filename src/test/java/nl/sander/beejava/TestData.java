package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import nl.sander.beejava.api.BeeConstructor;
import nl.sander.beejava.api.Ref;
import nl.sander.beejava.api.Version;
import nl.sander.beejava.flags.MethodAccessFlag;

import java.io.Serializable;

import static nl.sander.beejava.api.CodeLine.line;
import static nl.sander.beejava.api.Opcode.*;
import static nl.sander.beejava.flags.ClassAccessFlag.PUBLIC;

public class TestData {
    public static BeeClass emptyClass() {
        return BeeClass.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withConstructors(getBeeConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    public static BeeClass emptyClassWithInterface() {
        return BeeClass.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withInterfaces(Serializable.class)
                .withConstructors(getBeeConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    private static BeeConstructor getBeeConstructor() {
        BeeConstructor constructor = BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlag.PUBLIC)
                .withCode(
                        line(0, LOAD, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(5, RETURN))
                .build();
        return constructor;
    }
}

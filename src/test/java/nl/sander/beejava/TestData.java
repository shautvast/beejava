package nl.sander.beejava;

import nl.sander.beejava.api.*;
import nl.sander.beejava.flags.FieldAccessFlag;
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
                .withConstructors(createConstructor()) // There's no default constructor in beejava. The user must always add them
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
                .withConstructors(createConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    public static BeeClass createClassWithTwoReferencesToSomeClass() {
        BeeMethod print1 = BeeMethod.builder()
                .withAccessFlags(MethodAccessFlag.PUBLIC)
                .withCode(
                        line(0, GET, "java.lang.System","out"),
                        line(1, LD_CONST, "1"),
                        line(2, INVOKE, "java.io.PrintStream", "println", "(java.lang.String)"),
                        line(3, RETURN))
                .build();

        return BeeClass.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("ClassWithReferences")
                .withConstructors(createConstructor()) // There's no default constructor in beejava. The user must always add them
                .withMethods(print1)
                .build();
    }

    public static BeeClass createClassWithIntField() {
        BeeField intField = BeeField.builder()
                .withAccessFlags(FieldAccessFlag.PRIVATE)
                .withType(int.class)
                .withName("intField")
                .build();

        BeeParameter intValueParameter = BeeParameter.create(int.class, "intValue");

        BeeConstructor constructor = BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlag.PUBLIC)
                .withFormalParameters(intValueParameter)
                .withCode(
                        line(0, LD_VAR, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(2, LD_VAR, Ref.THIS),
                        line(3, LD_VAR, intValueParameter),
                        line(4, PUT, intField),
                        line(5, RETURN))
                .build();

        return BeeClass.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("IntBean")
                .withSuperClass(Object.class)
                .withFields(intField)
                .withConstructors(constructor)
                .build();
    }

    private static BeeConstructor createConstructor() {
        return BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlag.PUBLIC)
                .withCode(
                        line(0, LD_VAR, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(5, RETURN))
                .build();
    }
}

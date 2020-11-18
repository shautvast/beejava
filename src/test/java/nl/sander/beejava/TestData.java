package nl.sander.beejava;

import nl.sander.beejava.api.*;
import nl.sander.beejava.flags.FieldAccessFlags;
import nl.sander.beejava.flags.MethodAccessFlags;

import java.io.Serializable;

import static nl.sander.beejava.api.CodeLine.line;
import static nl.sander.beejava.api.Opcode.*;
import static nl.sander.beejava.flags.ClassAccessFlags.PUBLIC;
import static nl.sander.beejava.flags.ClassAccessFlags.SUPER;

public class TestData {
    public static BeeSource createEmptyClass() throws ClassNotFoundException {
        return BeeSource.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC, SUPER)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withConstructors(createDefaultConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    public static BeeSource emptyClassWithInterface() throws ClassNotFoundException {
        return BeeSource.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withInterfaces(Serializable.class)
                .withConstructors(createDefaultConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

    public static BeeSource createClassWithTwoReferencesToSomeClass() throws ClassNotFoundException {
        BeeMethod print1 = BeeMethod.builder()
                .withName("print1")
                .withAccessFlags(MethodAccessFlags.PUBLIC)
                .withCode(
                        line(GET, "java.lang.System","out"),
                        line(LD_CONST, "1"),
                        line(INVOKE, "java.io.PrintStream", "println", "java.lang.String"),
                        line(RETURN))
                .build();

//        INVOKE System.out.println("1")


        BeeMethod print2 = BeeMethod.builder()
                .withName("print2")
                .withAccessFlags(MethodAccessFlags.PUBLIC)
                .withCode(
                        line(GET, "java.lang.System","out"),
                        line(LD_CONST, "2"),
                        line(INVOKE, "java.io.PrintStream", "println", "java.lang.String"),
                        line(RETURN))
                .build();

        return BeeSource.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC, SUPER)
                .withSimpleName("ClassWithReferences")
                .withConstructors(createDefaultConstructor()) // There's no default constructor in beejava. The user must always add them
                .withMethods(print1, print2)
                .build();
    }

    public static BeeSource createClassWithField(Class<?> fieldType) throws ClassNotFoundException {
        BeeField field = BeeField.builder()
                .withAccessFlags(FieldAccessFlags.PRIVATE)
                .withType(fieldType)
                .withName("field")
                .build();

        BeeParameter parameter = BeeParameter.create(fieldType, "value");

        BeeConstructor constructor = BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlags.PUBLIC)
                .withFormalParameters(parameter)
                .withCode(
                        line(LD_VAR, Ref.THIS),
                        line(INVOKE, Ref.SUPER, "<init>", "()"),
                        line(LD_VAR, Ref.THIS),
                        line(LD_VAR, parameter),
                        line(PUT, field),
                        line(RETURN))
                .build();

        return BeeSource.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC)
                .withSimpleName("Bean")
                .withSuperClass(Object.class)
                .withFields(field)
                .withConstructors(constructor)
                .build();
    }

    public static BeeConstructor createDefaultConstructor() throws ClassNotFoundException {
            return BeeConstructor.builder()
                    .withAccessFlags(MethodAccessFlags.PUBLIC)
                    .withCode(
                            line(LD_VAR, Ref.THIS),
                        line(INVOKE, Ref.SUPER, "<init>", "()"),
                        line(RETURN))
                .build();
    }
}

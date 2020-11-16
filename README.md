# beejava
compiles java 'opcode' to bytecode.

project status: early stage
* At this moment a complete compile cycle is guaranteed (unittested) for a really simple class. 

```
BeeSource createEmptyClass() throws ClassNotFoundException {
        return BeeSource.builder()
                .withClassFileVersion(Version.V14)
                .withPackage("nl.sander.beejava.test")
                .withAccessFlags(PUBLIC, SUPER)
                .withSimpleName("EmptyBean")
                .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
                .withConstructors(createDefaultConstructor()) // There's no default constructor in beejava. The user must always add them
                .build();
    }

BeeConstructor createDefaultConstructor() throws ClassNotFoundException {
        return BeeConstructor.builder()
                .withAccessFlags(MethodAccessFlags.PUBLIC)
                .withCode(
                        line(0, LD_VAR, Ref.THIS),
                        line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                        line(5, RETURN))
                .build();
    }
```
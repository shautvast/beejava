# beejava
compiles java 'opcode' to bytecode.

What is 'opcode'?

The goal of the project is to let developers use a simplified version of standard java opcodes. For instance: 
instead of having to choose between INVOKE_SPECIAL, INVOKE_VIRTUAL, INVOKE_DYNAMIC and INVOKE_INTERFACE, developers can just write INVOKE and the compiler will figure out the correct instruction to put in the class file.

project status: early stage
* At this moment a complete compile cycle is guaranteed (unittested) for a really simple class. 

Code example below, but the API will undoubtedly change. 

```
BeeSource createEmptyClass() {
    return BeeSource.builder()
           .withClassFileVersion(Version.V14)
           .withPackage("nl.sander.beejava.test")
           .withAccessFlags(PUBLIC, SUPER)
           .withSimpleName("EmptyBean")
           .withSuperClass(Object.class) // Not mandatory, like in java sourcecode
           .withConstructors(createDefaultConstructor()) // There's no default constructor in beejava. The user must always add them
           .build();
}

BeeConstructor createDefaultConstructor() {
    return BeeConstructor.builder()
            .withAccessFlags(MethodAccessFlags.PUBLIC)
            .withCode(
                    line(0, LD_VAR, Ref.THIS),
                    line(1, INVOKE, Ref.SUPER, "<init>", "()"),
                    line(5, RETURN))
            .build();
 }
```
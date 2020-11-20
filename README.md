# beejava
Beejava is a code creation library, somewhat comparable to javassist or bytebuddy.  
* It let's you compile java 'opcode' to bytecode.
* It does not inspect or enhance existing bytecode, though it could be part of such functionality. 

What is 'opcode'?

The goal of the project is to let developers dynamically create classes, using a simplified version of standard java opcodes. For instance: 
instead of having to choose between:
- ```INVOKE_SPECIAL```
- ```INVOKE_VIRTUAL```
- ```INVOKE_DYNAMIC``` 
- ```INVOKE_INTERFACE```

developers can just write ```INVOKE``` and the compiler will figure out the correct instruction to put in the class file.

__project status:__

early stage
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
                    line(LD_VAR, Ref.THIS),
                    line(INVOKE, Ref.SUPER, "<init>", "()"),
                    line(RETURN))
            .build();
 }
```

and
```
BeeMethod print2 = BeeMethod.builder()
                .withName("print2")
                .withAccessFlags(MethodAccessFlags.PUBLIC)
                .withCode(
                        line(GET, "java.lang.System","out"),
                        line(LD_CONST, "2"),
                        line(INVOKE, "java.io.PrintStream", "println", "java.lang.String"),
                        line(RETURN))
                .build();
```

The latest commits are centered around the new feature that let's you write code like this:
```
class com.acme.SimpleBean(V15)
field private int value
constructor public()
  INVOKE super()
  RETURN
method public getValue() -> int
  RETURN this.value
method public setValue(int newValue)
  LOAD newValue
  PUT this.value
  RETURN
```
* text based instead of programmatic, although that will still be possible
* abstracts away most of the intricacies of working with a stack (although like bytecode it will still be stack based)
* indent-aware like python ;)


*Ideas about what's next*
* MORE opcodes
* invoke dynamic support (also in constant pool)
* support for exceptions, class attributes
* figure out a nicer, better api
* or instead drop this idea and let the developer write the raw bytecode. The constant pool would then be the only thing Beejava adds.
* create a readable file format for opcode files

package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;

public class TestData {
    public static BeeSource createEmptyClass()  {
        return SourceCompiler.compile("""
                class public nl.sander.beejava.test.EmptyBean
                constructor public()
                  INVOKE this.super()
                  RETURN
                """);
    }

    public static BeeSource emptyClassWithInterface() {
        return SourceCompiler.compile("""
                class public nl.sander.beejava.test.EmptyBean implements java.io.Serializable
                constructor public()
                  INVOKE this.super()V
                  RETURN
                """);
    }

    public static BeeSource createClassWithField(Class<?> fieldType)  {
        String template = """           
                class com.acme.SimpleBean(V15)
                field private %s field
                constructor public(%s arg)
                  INVOKE this.super()V
                  LOAD arg
                  PUT this.field
                  RETURN
                """;

        return SourceCompiler.compile(String.format(template, fieldType.getName(), fieldType.getName()));
    }

}

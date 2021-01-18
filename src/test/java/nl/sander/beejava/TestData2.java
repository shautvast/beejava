package nl.sander.beejava;

public class TestData2 {

    public final static String simpleBean= """           
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
            """;

    String constructor_original = """
                 0: aload_0
                 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
               
                 4: aload_0
                 5: new           #7                  // class nl/sander/beejava/ConstantPoolCreator
                 8: dup
                 9: invokespecial #9                  // Method nl/sander/beejava/ConstantPoolCreator."<init>":()V
                12: putfield      #10                 // Field constantPoolCreator:Lnl/sander/beejava/ConstantPoolCreator;
               
                15: aload_0
                16: aload_1
                17: putfield      #16                 // Field compiledClass:Lnl/sander/beejava/CompiledClass;
                20: aload_0
                21: new           #20                 // class nl/sander/beejava/ConstantPoolEntryCreator
                24: dup
                25: aload_1
                26: invokespecial #22                 // Method nl/sander/beejava/ConstantPoolEntryCreator."<init>":(Lnl/sander/beejava/CompiledClass;)V
                29: putfield      #25                 // Field constantPoolEntryCreator:Lnl/sander/beejava/ConstantPoolEntryCreator;
                32: return
            """;

    String constructor_opcope = """
            constructor nl.sander.beejava.Compiler(nl.sander.beejava.CompiledClass arg_0);
               INVOKE this.super()
               
               NEW nl.sander.beejava.ConstantPoolCreator()
               PUT this.constantPoolCreator
               
               LOAD arg_0
               PUT this.compiledClass
               NEW nl.sander.beejava.ConstantPoolEntryCreator(compiledClass)
               PUT this.constantPoolEntryCreator
            """;

    String method_original = """
            public void addMethods()
                 0: aload_0
                 1: getfield      #16                 // Field compiledClass:Lnl/sander/beejava/CompiledClass;
                 4: aload_0
                 5: getfield      #97 // Field codeAttributeNameEntry:Lnl/sander/beejava/constantpool/entry/Utf8Entry;
                 8: invokevirtual #101                // Method nl/sander/beejava/CompiledClass.addConstantPoolEntry:(Lnl/sander/beejava/constantpool/entry/ConstantPoolEntry;)V
                11: aload_0
                12: getfield      #16                 // Field compiledClass:Lnl/sander/beejava/CompiledClass;
                15: invokevirtual #59                 // Method nl/sander/beejava/CompiledClass.getSource:()Lnl/sander/beejava/api/BeeSource;
                18: invokevirtual #79                 // Method nl/sander/beejava/api/BeeSource.getMethods:()Ljava/util/Set;
                21: invokeinterface #135,  1          // InterfaceMethod java/util/Set.iterator:()Ljava/util/Iterator;
                26: astore_1
                27: aload_1
                28: invokeinterface #139,  1          // InterfaceMethod java/util/Iterator.hasNext:()Z
                33: ifeq          61
                36: aload_1
                37: invokeinterface #145,  1          // InterfaceMethod java/util/Iterator.next:()Ljava/lang/Object;
                42: checkcast     #149                // class nl/sander/beejava/api/BeeMethod
                45: astore_2
                46: aload_0
                47: getfield      #16                 // Field compiledClass:Lnl/sander/beejava/CompiledClass;
                50: aload_0
                51: aload_2
                52: invokevirtual #151                // Method createMethod:(Lnl/sander/beejava/CodeContainer;)Lnl/sander/beejava/classinfo/MethodInfo;
                55: invokevirtual #155                // Method nl/sander/beejava/CompiledClass.addMethod:(Lnl/sander/beejava/classinfo/MethodInfo;)V
                58: goto          27
                61: return
            """;

    String method_opcode = """
            public void addMethods()   
               INVOKE this.compiledClass.addConstantPoolEntry(this.codeAttributeNameEntry)
               
               INVOKE this.compiledClass.getSource()
               INVOKE getMethods()
               INVOKE iterator()
               STORE it
               
               :start-loop
               INVOKE it.hasNext
               IFEQ_false end
               INVOKE it.next()
               CAST nl.sander.beejava.api.BeeMethod
               STORE el
               INVOKE this.createMethod(el) 
               INVOKE this.compiledClass.addMethod()
               GOTO start-loop
               :end 
               RETURN
            """;

}

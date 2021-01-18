package nl.sander.beejava;

import nl.sander.beejava.api.BeeSource;

public final class OverallCompiler {

    private OverallCompiler(){
        //
    }

    public static byte[] compile(String sourcecode){
        BeeSource beeSource = SourceCompiler.compile(sourcecode);
        OpcodeTranslator.translate(beeSource);
        CompiledClass compiledClass = Compiler.compile(beeSource);
        return  BytecodeGenerator.generate(compiledClass);
    }
}

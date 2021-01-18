package nl.sander.bejava;

import nl.sander.bejava.api.BeSource;

/**
 * Contains all four passes necessary to go from source as text to bytecode.
 */
public final class OverallCompiler {

    private OverallCompiler() {
        //
    }

    public static byte[] compile(String sourcecode) {
        BeSource beSource = SourceCompiler.compile(sourcecode);
        OpcodeTranslator.translate(beSource);
        CompiledClass compiledClass = Compiler.compile(beSource);
        return BytecodeGenerator.generate(compiledClass);
    }
}

package nl.sander.beejava;

import nl.sander.beejava.api.BeeClass;
import org.junit.jupiter.api.Test;

public class ConstantPoolUniquenessTests {
    @Test
    public void test(){
        BeeClass someClass = TestData.createClassWithTwoReferencesToSomeClass();
        CompiledClass compiledClass = Compiler.compile(someClass);
        BytecodeGenerator.generate(compiledClass);
    }
}

package nl.sander.beejava;

import org.junit.jupiter.api.Test;

public class CompilerTests {
    @Test
    public void test(){
        Compiler.compile(TestData.emptyClass());
    }
}

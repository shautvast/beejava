package nl.sander.beejava;

import org.junit.jupiter.api.Test;

public class BytecodeGeneratorTests {
    @Test
    public void testEmpty(){
        BytecodeGenerator.compile(TestData.emptyClass());
    }

    @Test
    public void testInterface(){
        BytecodeGenerator.compile(TestData.emptyClassWithInterface());
    }
}

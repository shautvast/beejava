package nl.sander.beejava;

import nl.sander.beejava.apiv2.BeeSource;
import nl.sander.beejava.apiv2.SourceCompiler;
import org.junit.jupiter.api.Test;

public class SourceCompilerTest {

    @Test
    public void test(){
        BeeSource beeSource = new SourceCompiler(TestData2.simpleBean).doCompile();
        System.out.println(beeSource);
    }
}

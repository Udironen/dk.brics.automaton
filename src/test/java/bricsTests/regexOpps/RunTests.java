package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;

public class RunTests {


    @Test
    public void complementTest(){
        Automaton automatonA = Automaton.makeAnyString();
        Automaton complementA = automatonA.complement();
        Assert.assertTrue("complement did not except regex ", complementA.isEmpty());
    }
}

package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import org.junit.Assert;
import org.junit.Test;

public class OptionalTests {


    @Test
    public void complementTest(){
        Automaton automatonA = Automaton.makeAnyString();
        Automaton complementA = automatonA.complement();
        Assert.assertTrue("complement did not except regex ", complementA.isEmpty());
    }
}

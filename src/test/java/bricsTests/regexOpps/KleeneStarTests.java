package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;

public class KleeneStarTests {


    @Test
    public void kleeneStarTest(){
        Automaton automatonA = new RegExp("abc").toAutomaton();

        Automaton  automatonAStar = automatonA.repeat();
        Assert.assertTrue("kleene star did not except regex ", automatonAStar.run("abca"));
    }
}

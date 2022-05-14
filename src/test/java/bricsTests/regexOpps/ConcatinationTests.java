package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;

public class ConcatinationTests {


    @Test
    public void concatinationTest(){
        Automaton automatonA = new RegExp("a+").toAutomaton();
        Automaton automatonB = new RegExp(null).toAutomaton();
        Automaton concatinatAB = automatonA.concatenate(automatonB);
        Assert.assertTrue("concat did not except regex ", concatinatAB.run("aaabbbccc"));
    }
}

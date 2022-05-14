package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;


public class UnionTests {


    @Test
    public void unionTest(){
        Automaton automatonA = new RegExp("a*").toAutomaton();
        Automaton automatonB = new RegExp("b*").toAutomaton();
        Automaton unionAB = automatonA.union(automatonB);
        Assert.assertTrue("union did not except regex ", unionAB.run("aaa"));
    }


}

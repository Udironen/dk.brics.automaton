package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;

public class IntersectionTests {


    @Test
    public void intersectionTest(){
        Automaton automatonA = new RegExp("a*c*").toAutomaton();
        Automaton automatonB = new RegExp("b*c*").toAutomaton();
        Automaton intersectionAB = automatonA.intersection(automatonB);
        Assert.assertTrue("intersection did not except regex ", intersectionAB.run("bc"));
    }
}

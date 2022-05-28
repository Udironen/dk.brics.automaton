package test.java.bricsTests.basicOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperationsForTests;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import static test.java.bricsTests.BricsTestsSuite.NUM_OF_RANDOMS;

public class MinusTests {


	// BLACK BOX TESTING
    @Test
    public void basicTest(){
    	Automaton automatonA = (new RegExp("ab(c|d)*")).toAutomaton();
    	Automaton automatonB = (new RegExp("abc*")).toAutomaton();
        Automaton minus = BasicOperationsForTests.minus(automatonA, automatonB);
        Assert.assertTrue("minus did not except regex 'abd'", minus.run("abd"));
        Assert.assertFalse("minus did not except regex 'abd'", minus.run("abc"));
    }
    
    @Test
    public void minusRandomTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkMinusRandomTest(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkMinusRandomTest(Validator validator, RandomRegex reg1, RandomRegex reg2){
        Automaton automatonA = new RegExp(reg1.getRegex()).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex()).toAutomaton();
        Automaton minus = BasicOperationsForTests.minus(automatonA, automatonB);
        String yesA = automatonA.getShortestExample(true);
        String noA = automatonA.getShortestExample(false);
        String yesB = automatonB.getShortestExample(true);

        if (automatonB.run(yesA))
            validator.addNegativeCheck(minus.run(yesA),
                "regex: " + reg1.getRegex() + " minus " + reg2.getRegex() + " shouldn't accept " + yesA +" because it belongs to A and also to B ");
        else
            validator.addCheck(minus.run(yesA),
                    "regex: " + reg1.getRegex() + " minus " + reg2.getRegex() + " should accept " + yesA +" because it belongs to A and not to B ");

        validator.addNegativeCheck(minus.run(noA),
                "regex: " + reg1.getRegex() + " minus " + reg2.getRegex() + "shouldn't accept " + noA+" because it is not in A");
        validator.addNegativeCheck(minus.run(yesB),
                "regex: " + reg1.getRegex() + " minus " + reg2.getRegex() + "shouldn't accept " + yesB+" because it is in B");
    }
    
    @Test
    public void emptySetTest1(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperationsForTests.minus(automatonA, Automaton.makeEmpty());
        Assert.assertTrue("minus did not except regex 'talya'", minus.run("talya"));
        Assert.assertFalse("minus did not except regex 'ohad'", minus.run("ohad"));
    }
    
    @Test
    public void emptySetTest2(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperationsForTests.minus(Automaton.makeEmpty(), automatonA);
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    
    @Test
    public void allSetTest1(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperationsForTests.minus(automatonA, Automaton.makeAnyString());
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    
    @Test
    public void allSetTest2(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperationsForTests.minus(Automaton.makeAnyString(), automatonA);
        Assert.assertFalse("minus did except 'talya'", minus.run("talya"));
    }
    
    // WHITE BOX TEST
//    static public Automaton minus(Automaton a1, Automaton a2) {
//		if (a1.isEmpty() || a1 == a2)
//			return BasicAutomata.makeEmpty();
//		if (a2.isEmpty())
//			return a1.cloneIfRequired();
//		if (a1.isSingleton()) {
//			if (a2.run(a1.singleton))
//				return BasicAutomata.makeEmpty();
//			else
//				return a1.cloneIfRequired();
//		}
//		return intersection(a1, a2.complement());
//	}
    @Test
    public void a1a2equal(){
    	Automaton a1 = Automaton.makeString("talya");
        Automaton minus = BasicOperationsForTests.minus(a1, a1);
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    @Test
    public void a1NotEmptya2IsEmpty(){
    	Automaton a1 = Automaton.makeString("talya");
    	Automaton a2 = Automaton.makeEmpty();
        Automaton minus = BasicOperationsForTests.minus(a1, a2);
        Assert.assertEquals("minus is not eq to a1", minus, a1);
    }
    @Test
    public void a1IsSingletona2SingletonTest(){
    	Automaton a1 = Automaton.makeString("talya");
    	Automaton a2 = Automaton.makeString("talya");
        Automaton minus = BasicOperationsForTests.minus(a1, a2);
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    @Test
    public void a1IsSingletona2NotSingletonTest(){
    	Automaton a1 = Automaton.makeString("talya");
    	Automaton a2 = (new RegExp("abc*")).toAutomaton();
        Automaton minus = BasicOperationsForTests.minus(a1, a2);
        Assert.assertEquals("minus failed", minus, BasicOperationsForTests.intersection(a1, a2.complement()));
    }
    
    
}

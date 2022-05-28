package test.java.bricsTests.basicOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

public class MinusTests {


	// BLACK BOX TESTING
    @Test
    public void basicTest(){
    	Automaton automatonA = (new RegExp("ab(c|d)*")).toAutomaton();
    	Automaton automatonB = (new RegExp("abc*")).toAutomaton();
        Automaton minus = BasicOperations.minus(automatonA, automatonB);
        Assert.assertTrue("minus did not except regex 'abd'", minus.run("abd"));
        Assert.assertFalse("minus did not except regex 'abd'", minus.run("abc"));
    }
    
    @Test
    public void minusRandomTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkMinusRandomTest(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkMinusRandomTest(Validator validator, RandomRegex reg1, RandomRegex reg2){
        Automaton automatonA = new RegExp(reg1.getRegex()).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex()).toAutomaton();
        Automaton minus = BasicOperations.minus(automatonA, automatonB);
        String yesA = automatonA.getShortestExample(true);
        String noA = automatonA.getShortestExample(false);
        String yesB = automatonB.getShortestExample(true);

        validator.addCheck(minus.run(yesA),
                "regex: " + reg1.getRegex() + " and " + reg2.getRegex() + " did not work " + yesA);
        validator.addCheck(!minus.run(noA),
                "regex: " + reg1.getRegex() + " and " + reg2.getRegex() + " did not work " + noA);
        validator.addCheck(!minus.run(yesB),
                "regex: " + reg1.getRegex() + " and " + reg2.getRegex() + " did not work " + yesB);
    }
    
    @Test
    public void emptySetTest1(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperations.minus(automatonA, Automaton.makeEmpty());
        Assert.assertTrue("minus did not except regex 'talya'", minus.run("talya"));
        Assert.assertFalse("minus did not except regex 'ohad'", minus.run("ohad"));
    }
    
    @Test
    public void emptySetTest2(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperations.minus(Automaton.makeEmpty(), automatonA);
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    
    @Test
    public void allSetTest1(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperations.minus(automatonA, Automaton.makeAnyString());
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    
    @Test
    public void allSetTest2(){
    	Automaton automatonA = Automaton.makeString("talya");
        Automaton minus = BasicOperations.minus(Automaton.makeAnyString(), automatonA);
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
        Automaton minus = BasicOperations.minus(a1, a1);
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    @Test
    public void a1NotEmptya2IsEmpty(){
    	Automaton a1 = Automaton.makeString("talya");
    	Automaton a2 = Automaton.makeEmpty();
        Automaton minus = BasicOperations.minus(a1, a2);
        Assert.assertEquals("minus is not eq to a1", minus, a1);
    }
    @Test
    public void a1IsSingletona2SingletonTest(){
    	Automaton a1 = Automaton.makeString("talya");
    	Automaton a2 = Automaton.makeString("talya");
        Automaton minus = BasicOperations.minus(a1, a2);
        Assert.assertTrue("minus is not empty", minus.isEmpty());
    }
    @Test
    public void a1IsSingletona2NotSingletonTest(){
    	Automaton a1 = Automaton.makeString("talya");
    	Automaton a2 = (new RegExp("abc*")).toAutomaton();
        Automaton minus = BasicOperations.minus(a1, a2);
        Assert.assertEquals("minus failed", minus, BasicOperations.intersection(a1, a2.complement()));
    }
    
    
}

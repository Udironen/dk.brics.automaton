package test.java.bricsTests.basicOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;
import org.junit.Assert;
import org.junit.Test;

public class OptionalTests {


	// BLACK BOX TESTS
	@Test
    public void emptyCaseTest(){
        Automaton a = Automaton.makeEmpty();
        Automaton b = Automaton.makeEmptyString();
        Automaton opt = BasicOperations.optional(a);
        Assert.assertEquals(opt, b);
    }

	@Test
    public void anyCaseTest(){
        Automaton a = Automaton.makeAnyString();
        Automaton opt = BasicOperations.optional(a);
        Assert.assertEquals(opt, a);
    }

	@Test
    public void RandomRegexCaseTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        	Automaton opt = BasicOperations.optional(a);
        	boolean bool = (!(a.getShortestExample(true).equals("")))&&((opt.getShortestExample(true).equals("")));
            validator.addCheck(bool , "failed on regex: " + reg1.getRegex());
            bool = (opt.minus(Automaton.makeEmptyString()).equals(a));
            validator.addCheck(bool , "a.opt()-'' is not equal to a: " + reg1.getRegex());
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
	}
	
	//WHITE BOX TESTS
	@Test
	public void initialShouldRejectTest()
	{
		State init = new State();
    	State second = new State();
    	init.setAccept(false);
    	second.setAccept(true);
    	init.addTransition(new Transition('0', second));
    	second.addTransition(new Transition('1', init));
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
		automatonA =  BasicOperations.optional(automatonA);
		Assert.assertFalse("it accepts a string i used to not accept", BasicOperations.run(automatonA, "01"));
		
	}
}

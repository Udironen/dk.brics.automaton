package test.java.bricsTests.basicOpps;

import dk.brics.automaton.*;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import java.util.ArrayList;
import java.util.Collection;

import static test.java.bricsTests.BricsTestsSuite.NUM_OF_RANDOMS;

public class RunTests {


	// BLACK BOX TESTS
	@Test
	public void acceptRegexRandomTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a1 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            checkStringInAutomate(a1, validator, a1.getShortestExample(true));
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	@Test
	public void rejectRegexRandomTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a1 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            checkStringNotInAutomate(a1, validator, a1.getShortestExample(false));
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	
    public void  acceeptAllRandomTest(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = Automaton.makeAnyString();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            String s = RandomRegex.getRandString();
            checkStringInAutomate(automatonA, validator, s);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	private void checkStringInAutomate(Automaton automatonA, Validator validator, String s){
        validator.addCheck(BasicOperationsForTests.run(automatonA, s) ,
                "the string " + s + "didn't get accepted but is should");
    }
	
	@Test
    public void emptySetRandomTest(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = Automaton.makeEmpty();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            String s = RandomRegex.getRandString();
            checkStringNotInAutomate(automatonA, validator, s);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	private void checkStringNotInAutomate(Automaton automatonA, Validator validator, String s){
        validator.addCheck(!BasicOperationsForTests.run(automatonA, s) ,
                "the string " + s + "got accepted but is shouldnt");
    }
    
	@Test
    public void SingletonRandomTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            String s = RandomRegex.getRandString();
            Automaton automatonA = Automaton.makeString(s);
            checkStringInAutomate(automatonA, validator, s);
            checkStringNotInAutomate(automatonA, validator, "");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	// WHITE BOX TESTS
	// (a.isSingleton()) (a.deterministic) else
	@Test
	public void SingletonTest(){
		Automaton automatonA = Automaton.makeString("talya");
		Assert.assertTrue(BasicOperationsForTests.run(automatonA, "talya"));
		Assert.assertFalse(BasicOperationsForTests.run(automatonA, "ohad"));	
    }
	
	@Test
	public void determenisticTest(){
		State init = new State();
    	State fin = new State();
    	init.setAccept(false);
    	fin.setAccept(true);
    	init.addTransition(new Transition('t', fin));
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	if (automatonA.getSingleton() != null ||  !automatonA.isDeterministic()) {
    		Assert.fail("bad test");
    	}
    	Assert.assertTrue(BasicOperationsForTests.run(automatonA, "t"));
		Assert.assertFalse(BasicOperationsForTests.run(automatonA, "o"));	
    }
	
	@Test
	public void notDetermenisticTest(){
		State init = new State();
    	State fin = new State();
    	init.setAccept(false);
    	fin.setAccept(true);
    	init.addTransition(new Transition('t', fin));
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	Collection<StatePair> c = new ArrayList<>();
    	c.add(new StatePair(init,fin));
    	automatonA.addEpsilons(c);
    	if (automatonA.getSingleton() != null ||  automatonA.isDeterministic()) {
    		Assert.fail("bad test");
    	}
    	Assert.assertTrue(BasicOperationsForTests.run(automatonA, "t"));
    	Assert.assertTrue(BasicOperationsForTests.run(automatonA, ""));
		Assert.assertFalse(BasicOperationsForTests.run(automatonA, "o"));	
    }

}

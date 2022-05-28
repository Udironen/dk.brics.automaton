package test.java.bricsTests.regexOpps;

import dk.brics.automaton.*;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class RunTests {


	// BLACK BOX TESTS
	@Test
    public void acceeptAllRandomTest(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = Automaton.makeAnyString();
        for (int i = 0; i < 1000; ++i){
            String s = RandomRegex.getRandString();
            checkStringInAutomate(automatonA, validator, s);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	private void checkStringInAutomate(Automaton automatonA, Validator validator, String s){
        validator.addCheck(BasicOperations.run(automatonA, s) ,
                "the string " + s + "didn't get accepted");
    }
	
	@Test
    public void emptySetRandomTest(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = Automaton.makeEmpty();
        for (int i = 0; i < 1000; ++i){
            String s = RandomRegex.getRandString();
            checkStringNotInAutomate(automatonA, validator, s);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	private void checkStringNotInAutomate(Automaton automatonA, Validator validator, String s){
        validator.addCheck(!BasicOperations.run(automatonA, s) ,
                "the string " + s + "didn't get accepted");
    }
    
	@Test
    public void SingletonRandomTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
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
		Assert.assertTrue(BasicOperations.run(automatonA, "talya"));
		Assert.assertFalse(BasicOperations.run(automatonA, "ohad"));	
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
    	Assert.assertTrue(BasicOperations.run(automatonA, "t"));
		Assert.assertFalse(BasicOperations.run(automatonA, "o"));	
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
    	Assert.assertTrue(BasicOperations.run(automatonA, "t"));
    	Assert.assertTrue(BasicOperations.run(automatonA, ""));
		Assert.assertFalse(BasicOperations.run(automatonA, "o"));	
    }

}

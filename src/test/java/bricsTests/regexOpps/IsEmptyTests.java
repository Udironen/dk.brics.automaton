package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import org.junit.Assert;
import org.junit.Test;

public class IsEmptyTests {


	//BLACK BOX TESTS
    @Test
    public void makeEmpty(){
        Automaton automatonA = Automaton.makeEmpty();
        Assert.assertTrue("isEmpty did not recognise Automaton.makeEmpty()", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void emptlyRegExp(){
    	Automaton automatonA = new RegExp("").toAutomaton();
    	Assert.assertTrue("isEmpty did not recognise empty regexp", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void nullRegExp(){
    	Automaton automatonA = new RegExp(null).toAutomaton();
    	Assert.assertTrue("isEmpty did not recognise null regexp", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void notEmptlyRegExp(){
    	Automaton automatonA = new RegExp("a").toAutomaton();
    	Assert.assertFalse("isEmpty thought \"a\" regexp is empty", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void oneAcceptingState(){
    	State init = new State();
    	init.setAccept(true);
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	Assert.assertFalse("isEmpty though oneAcceptingState is empty", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void NoAcceptingState(){
    	State init = new State();
    	init.setAccept(false);
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	Assert.assertTrue("isEmpty did not recognise NoAcceptingState", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void newAutomaton(){
    	Automaton automatonA = new Automaton();
    	Assert.assertTrue("isEmpty did not recognise newAutomaton", BasicOperations.isEmpty(automatonA));
    }
    
    @Test
    public void randomRegexAndComplementTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            String regex = RandomRegex.getRandRegEx();
            checkrandomRegexAndComplementTest(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
    
    private void checkrandomRegexAndComplementTest(Validator validator, String reg1){
        Automaton automatonA = new RegExp(reg1).toAutomaton();
        Automaton automatonB = automatonA.complement();
        Automaton intersection = automatonA.intersection(automatonB);
        validator.addCheck(BasicOperations.isEmpty(intersection) ,
                "the intersection of regex: " + reg1 + " and it's complement did not result in empty language although is should have.");
    }
    
    @Test
    public void randomRegexNotEmptyTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            String regex = RandomRegex.getRandRegEx();
            checkrandomRegexNotEmptyTest(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
    
    private void checkrandomRegexNotEmptyTest(Validator validator, String reg1){
        Automaton automatonA = new RegExp(reg1).toAutomaton();
        if (automatonA.getShortestExample(true)!=null) {
        	validator.addCheck(!BasicOperations.isEmpty(automatonA) ,
        			"the regex: " + reg1 + " resulted in empty language although is shouldnt have.");
        }   	
    }
}


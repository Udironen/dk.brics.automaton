package test.java.bricsTests.basicOpps;

import dk.brics.automaton.*;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import org.junit.Assert;
import org.junit.Test;

import static test.java.bricsTests.BricsTestsSuite.NUM_OF_RANDOMS;

public class IsEmptyTests {


	//BLACK BOX TESTS
    @Test
    public void makeEmpty(){
        Automaton automatonA = Automaton.makeEmpty();
        Assert.assertTrue("isEmpty did not recognise Automaton.makeEmpty()", BasicOperations.isEmpty(automatonA));
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
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            String regex = RandomRegex.getRandRegex().getRegex();
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
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            String regex = RandomRegex.getRandRegex().getRegex();
            checkrandomRegexNotEmptyTest(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
    
    private void checkrandomRegexNotEmptyTest(Validator validator, String reg1){
        Automaton automatonA = new RegExp(reg1).toAutomaton();
        validator.addCheck(!BasicOperations.isEmpty(automatonA) ,
    			"the regex: " + reg1 + " resulted in empty language although is shouldnt have.");  	
    }
    
    // WHITE BOX TESTS



    /***
     * this tests validate all conditions in the source code:
     * public static boolean isEmpty(Automaton a) {
     * 		if (a.isSingleton())
     * 			return false;
     * 		return !a.initial.accept && a.initial.transitions.isEmpty();
     *        }
     */
    @Test
    public void makeSingletonTest(){ // a.isSingleton()==True 
    	Automaton automatonA = BasicAutomata.makeString("Talya");
    	Assert.assertFalse("condition 1 in isEmpty failed", BasicOperationsForTests.isEmpty(automatonA));
    }
    
    @Test
 // a.isSingleton()==False,  !a.initial.accept==False
    public void notSingletonInitialAccept(){ 
    	State init = new State();
    	init.setAccept(true);
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	Assert.assertFalse("condition 2 in isEmpty failed", BasicOperationsForTests.isEmpty(automatonA));
    }
    
    // a.isSingleton()==False,  !a.initial.accept==True, a.initial.transitions.isEmpty()==False
    @Test
    public void notSingletonNotInitialAccept(){
    	State init = new State();
    	State fin = new State();
    	init.setAccept(false);
    	fin.setAccept(false);
    	init.addTransition(new Transition('t', fin));
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	Assert.assertFalse("condition 3 in isEmpty failed", BasicOperationsForTests.isEmpty(automatonA));
    }

    
 // a.isSingleton()==False,  !a.initial.accept==True, a.initial.transitions.isEmpty()==True
    @Test
    public void notSingletonNotInitialAcceptIsEmptyTrans(){
    	State init = new State();
    	init.setAccept(false);
    	Automaton automatonA = new Automaton();
    	automatonA.setInitialState(init);
    	Assert.assertTrue("condition 2+3 in isEmpty failed", BasicOperationsForTests.isEmpty(automatonA));
    }
    
    
}


package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import org.junit.Assert;
import org.junit.Test;

public class SubsetOfTests {


	// BLACK BOX TESTS
	@Test
    public void a1Emptya2EmptyCaseTest(){
		Automaton a1 = Automaton.makeEmpty();
    	Automaton a2 = Automaton.makeEmpty();
        Assert.assertTrue("emptyset should be subset of emptyset", BasicOperations.subsetOf(a1, a2));
    }
	
	@Test
    public void a1EmptyCaseTest(){
        Validator validator = Validator.getValidator();
        Automaton a1 = Automaton.makeEmpty();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a2 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            validator.addCheck(BasicOperations.subsetOf(a1, a2) ,
                    "emptyset should be subset of " + reg1.getRegex());
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	@Test
    public void a2EmptyCaseTest(){
        Validator validator = Validator.getValidator();
        Automaton a2 = Automaton.makeEmpty();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a1 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            validator.addCheck((!a1.isEmpty())&&(!BasicOperations.subsetOf(a1, a2)) ,
                    reg1.getRegex()+" shouldn't be subset of emptyset");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	@Test
    public void a1AnyCaseTest(){
        Validator validator = Validator.getValidator();
        Automaton a1 = Automaton.makeAnyString();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a2 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            validator.addCheck((!a2.complement().isEmpty())&&(!BasicOperations.subsetOf(a1, a2)) ,
                    "AnyString shouldn't be subset of " + reg1.getRegex());
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	@Test
    public void a2AnyCaseTest(){
        Validator validator = Validator.getValidator();
        Automaton a2 = Automaton.makeAnyString();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a1 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            validator.addCheck(BasicOperations.subsetOf(a1, a2) ,
                    reg1.getRegex()+" should be subset of AnyString");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	@Test
    public void a1eqa2Test(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a1 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        	Automaton a2 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            validator.addCheck(BasicOperations.subsetOf(a1, a2) ,
                    reg1.getRegex()+" isn't a subset of itself");
            validator.addCheck(BasicOperations.subsetOf(a2, a1) ,
                    reg1.getRegex()+" isn't a subset of itself");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

	@Test
    public void SpecificTrueCaseTest1(){
		Automaton a1 = new RegExp("01(23)+").toAutomaton();
    	Automaton a2 = new RegExp("(012)*(32)*3+").toAutomaton();
        Assert.assertTrue("\"01(23)+\" should be subset of \"(012)*(32)*3+\"", BasicOperations.subsetOf(a1, a2));
    }
	@Test
    public void SpecificTrueCaseTest2(){
		Automaton a1 = new RegExp("(012)+").toAutomaton();
    	Automaton a2 = new RegExp("(012)*(34)*012").toAutomaton();
        Assert.assertTrue("\"(012)+\" should be subset of \"(012)*(34)*012\"", BasicOperations.subsetOf(a1, a2));
    }
	@Test
    public void SpecificTrueCaseTest3(){
		Automaton a1 = new RegExp("012").toAutomaton();
    	Automaton a2 = new RegExp("(012)*(34)*").toAutomaton();
        Assert.assertTrue("\"012\" should be subset of \"(012)*(34)*\"", BasicOperations.subsetOf(a1, a2));
    }
	
	
	@Test
    public void SpecificFalseCaseTest1(){
		Automaton a1 = new RegExp("01(23)*").toAutomaton();
    	Automaton a2 = new RegExp("(01)*(23)+").toAutomaton();
        Assert.assertTrue("\"01(23)*\" shouldn't be subset of \"(012)*(32)*3+\"", BasicOperations.subsetOf(a1, a2));
    }
	@Test
    public void SpecificFalseCaseTest2(){
		Automaton a1 = new RegExp("(012)*").toAutomaton();
    	Automaton a2 = new RegExp("(012)+").toAutomaton();
        Assert.assertTrue("\"(012)*\" shouldn't be subset of \"(012)+\"", BasicOperations.subsetOf(a1, a2));
    }
	@Test
    public void SpecificFalseCaseTest3(){
		Automaton a1 = new RegExp("(012)*(34)*").toAutomaton();
    	Automaton a2 = new RegExp("(01)*(234)*").toAutomaton();
        Assert.assertTrue("\"(012)*(34)*\" shouldn't be subset of \"(01)*(234)*\"", BasicOperations.subsetOf(a1, a2));
    }
	
	// WHITE BOX TESTS
	// a1==a2
	@Test
    public void a1eqeqa2Test(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
        	RandomRegex reg1 = RandomRegex.getRandRegex();
        	Automaton a1 = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        	Automaton a2 = a1;
            validator.addCheck(BasicOperations.subsetOf(a1, a2) ,
                    reg1.getRegex()+" isn't a subset of itself");
            validator.addCheck(BasicOperations.subsetOf(a2, a1) ,
                    reg1.getRegex()+" isn't a subset of itself");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	// a1.isSingleton()
	@Test
    public void a1isSingletonTest(){

    }
}

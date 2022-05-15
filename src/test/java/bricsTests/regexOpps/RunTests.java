package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import org.junit.Assert;
import org.junit.Test;

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
        validator.addCheck(automatonA.run(s) ,
                "the string " + s + "didn't get accepted");
    }
	
	@Test
    public void emptySetRandomTest(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = Automaton.makeAnyString();
        for (int i = 0; i < 1000; ++i){
            String s = RandomRegex.getRandString();
            checkStringNotInAutomate(automatonA, validator, s);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }
	
	private void checkStringNotInAutomate(Automaton automatonA, Validator validator, String s){
        validator.addCheck(!automatonA.run(s) ,
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
	
}

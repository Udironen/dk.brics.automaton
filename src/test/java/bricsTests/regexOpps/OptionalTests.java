package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

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
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
	}
}

package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;

import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import java.util.List;


public class UnionTests {


    @Test
    public void checkFirstOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegEx();
            RandomRegex secondRegex = RandomRegex.getRandRegEx();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    @Test
    public void checkSecondOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegEx();
            RandomRegex secondRegex = RandomRegex.getRandRegEx();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkFirstRegexOfUnion(Validator validator, RandomRegex reg1, RandomRegex reg2, boolean first){
        Automaton automatonA = new RegExp(reg1.getRegex()).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex()).toAutomaton();
        Automaton unionAB = BasicOperations.union(automatonA, automatonB);
        List<String> strings = first ? reg1.getStrings() : reg2.getStrings();
        for (String aOrBString : strings) {
            System.out.println("regex: " + reg1.getRegex() + " union " + reg2.getRegex() + "\n" +
                    "random string from" + (first ? " first " : " second ") + "regex domain: " + aOrBString + "\n");
            validator.addCheck(unionAB.run(aOrBString),
                    "regex: " + reg1.getRegex() + " union " + reg2.getRegex() + " did not accept string " + aOrBString
                            + " although is should have.");
        }
    }


    @Test
    public void anotherUnionTest(){
        Automaton automatonA = new RegExp("a").toAutomaton();
        Automaton automatonB = new RegExp("b").toAutomaton();
        Automaton ab = new RegExp("a^b").toAutomaton();
        Assert.assertTrue(ab.run("ab"));
    }

    public boolean checkUnionOperation(String reg1, String reg2, String checkString){
        Automaton automatonA = new RegExp(reg1).toAutomaton();
        Automaton automatonB = new RegExp(reg2).toAutomaton();
        Automaton oppAutomaton = BasicOperations.union(automatonA, automatonB);
        return oppAutomaton.run(checkString);
    }

    public void assertTrueUnion(String reg1, String reg2, String checkString, boolean except){
        Assert.assertTrue( "union operation with regex " + reg1 + ", " + reg2 +
                        ", did not except string " + checkString,
                except);
    }

}

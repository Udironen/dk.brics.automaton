package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;

import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;


public class UnionTests {


    @Test
    public void checkFirstOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            String firstRegex = RandomRegex.getRandRegEx();
            String secondRegex = RandomRegex.getRandRegEx();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    @Test
    public void checkSecondOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            String firstRegex = RandomRegex.getRandRegEx();
            String secondRegex = RandomRegex.getRandRegEx();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkFirstRegexOfUnion(Validator validator, String reg1, String reg2, boolean first){
        Automaton automatonA = new RegExp(reg1).toAutomaton();
        Automaton automatonB = new RegExp(reg2).toAutomaton();
        Automaton unionAB = BasicOperations.union(automatonA, automatonB);
        String AorBString = first ? automatonA.getShortestExample(true) :
                                    automatonB.getShortestExample(true);
        System.out.println("regex: " + reg1 + " union " + reg2 + "\n" +
                "random string from" + (first ? "first " : "second ") + "regex domain: " + AorBString);
        validator.addCheck(unionAB.run(AorBString) ,
                "regex: " + reg1 + " union " + reg2 + " did not accept string " + AorBString
                        + "although is should have.");
    }


    @Test
    public void anotherUnionTest(){

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

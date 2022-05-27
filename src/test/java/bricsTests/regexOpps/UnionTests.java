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

    /***
     * this test check if strings that belong to the first regex domain of the union is part of the new union domain
     */
    @Test
    public void checkFirstOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test check if strings that belong to the second regex domain of the union is part of the new union domain
     */
    @Test
    public void checkSecondOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test check if strings that does not belong to the regex domains of the union is NOT part of the new union domain
     */
    @Test
    public void checkFirstAndSecondComplementNotInUnion(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkComplementRegexOfUnionNotInUnion(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the union is the empty regex
     * this test check if strings that belong to the first regex domain of the union is part of the new union domain
     */
    @Test
    public void checkSecondOfUnionIsEmptyTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getEmptyRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the union is the empty regex
     * this test check if strings that belong to the complement of the first regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkSecondOfUnionIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getEmptyRegex();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is the empty regex
     * this test check if strings that belong to the second regex domain of the union is part of the new union domain
     */
    @Test
    public void checkFirstOfUnionIsEmptyTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getEmptyRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is the empty regex
     * this test check if strings that belong to the complement of the second regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkFirstOfUnionIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getEmptyRegex();
            RandomRegex reg2 = RandomRegex.getRandRegex();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test both regexes in the union are the empty regex
     * this test check if strings that there are no strings that accepted after the union
     */
    @Test
    public void checkUnionIsEmptyTest(){
        Validator validator = Validator.getValidator();
        RandomRegex firstRegex = RandomRegex.getEmptyRegex();
        RandomRegex secondRegex = RandomRegex.getEmptyRegex();
        for (int i = 0; i < 1000; ++i){
            checkComplementRegexOfUnionNotInUnion(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test both regexes in the union are the empty regex
     * this test check if union.isEmpty == true
     */
    @Test
    public void checkUnionIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = new RegExp("#", RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp("#", RegExp.ALL).toAutomaton();
        Automaton unionAB = BasicOperations.union(automatonA, automatonB);
        validator.addCheck(unionAB.isEmpty(), "unionAB.isEmpty() return false");
        validator.addCheck(unionAB.getAcceptStates().isEmpty() , "accept states are not empty");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is a singleton regex
     * this test check if strings that belong to the complement of the regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkFirstOfUnionIsSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            RandomRegex reg2 = RandomRegex.getRandRegex();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is a singleton regex
     * this test check if strings that belong to the complement of the regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkSecondOfUnionIsSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getRandRegexSingleton();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is a singleton regex
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkFirstOfUnionIsSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the union is a singleton regex
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkSecondOfUnionIsSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the union is a singleton regex
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkBothRegexesOfUnionAreSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            RandomRegex secondRegex = RandomRegex.getRandRegexSingleton();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the the both regexes in the union is singleton regex
     * this test check if strings that belong to the complement of the regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkBothRegexesOfUnionAreSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            RandomRegex reg2 = RandomRegex.getRandRegexSingleton();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the union is singleton regex
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkBothRegexesOfUnionAreTheSameSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            checkFirstRegexOfUnion(validator, firstRegex, firstRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the union is a singleton regex
     * this test check if strings that belong to the complement of the regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkBothRegexesOfUnionAreTheSameSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg1);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


    //****************************************************************************************************************//

    /***
     * in this test the second regex in the union is the empty string regex
     * this test check if strings that belong to the first regex domain of the union is part of the new union domain
     */
    @Test
    public void checkSecondOfUnionIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getEmptyStringRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the union is the empty string regex
     * this test check if strings that belong to the complement of the first regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkSecondOfUnionIsEmptyStringTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getEmptyStringRegex();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is the empty string regex
     * this test check if strings that belong to the second regex domain of the union is part of the new union domain
     */
    @Test
    public void checkFirstOfUnionIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getEmptyStringRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union is the empty string regex
     * this test check if strings that belong to the complement of the second regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkFirstOfUnionIsEmptyStringTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getEmptyStringRegex();
            RandomRegex reg2 = RandomRegex.getRandRegex();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test both regexes in the union are the empty string regex
     * this test check if strings that there are no strings that accepted after the union
     */
    @Test
    public void checkUnionIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        RandomRegex firstRegex = RandomRegex.getEmptyStringRegex();
        RandomRegex secondRegex = RandomRegex.getEmptyStringRegex();
        for (int i = 0; i < 1000; ++i){
            checkComplementRegexOfUnionNotInUnion(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test both regexes in the union are the empty string regex
     * this test check if the empty string is in the union
     */
    @Test
    public void checkUnionIsEmptyStringTest2(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton unionAB = BasicOperations.union(automatonA, automatonB);
        validator.addCheck(unionAB.run(""), "unionAB.run(\"\") return false");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

//***********************************************************************************************************

    /***
     * in this test the second regex in the union contains the empty string
     * this test check if strings that belong to the complement of the regex domain of the union is NOT part of the new union domain
     */
    @Test
    public void checkSecondOfUnionContainsEmptyStringTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getRandRegexWithEmptyString();
            checkComplementRegexOfUnionNotInUnion(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the union contains the empty string
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkFirstOfUnionContainsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexWithEmptyString();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the union contains the empty string
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkSecondOfUnionContainsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandSingletonWithEmptyString();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the union contains the empty string
     * this test check if strings that belong to the regex domain of the union is part of the new union domain
     */
    @Test
    public void checkBothRegexesOfUnionContainsEmptyStringTest() {
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i) {
            RandomRegex firstRegex = RandomRegex.getRandRegexWithEmptyString();
            RandomRegex secondRegex = RandomRegex.getRandRegexWithEmptyString();
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, false);
            checkFirstRegexOfUnion(validator, firstRegex, secondRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkFirstRegexOfUnion(Validator validator, RandomRegex reg1, RandomRegex reg2, boolean checkFirst){
        Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
        Automaton unionAB = BasicOperations.union(automatonA, automatonB);
        List<String> strings = checkFirst ? reg1.getStrings() : reg2.getStrings();
        for (String aOrBString : strings) {
            System.out.println("regex: " + reg1.getRegex() + " union " + reg2.getRegex() + "\n" +
                    "random string from" + (checkFirst ? " first " : " second ") + "regex domain: " + aOrBString + "\n");
            validator.addCheck(unionAB.run(aOrBString),
                    "regex: " + reg1.getRegex() + " union " + reg2.getRegex() + " did not accept string " + aOrBString
                            + " although is should have.");
        }
    }

    private void checkComplementRegexOfUnionNotInUnion(Validator validator, RandomRegex reg1, RandomRegex reg2){
        Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
        RandomRegex reg3 = RandomRegex.getRandRegex();
        List<String> strings = reg3.getStrings();
        Automaton unionAB = BasicOperations.union(automatonA, automatonB);
        for (String reg3String : strings) {
            if (automatonA.run(reg3String) || automatonB.run(reg3String)) continue;
            System.out.println("regex: " + reg1.getRegex() + " union " + reg2.getRegex() + "\n" +
                    "random string from third regex domain: " + reg3String + "\n");
            validator.addNegativeCheck(unionAB.run(reg3String),
                    "regex: " + reg1.getRegex() + " union " + reg2.getRegex() + " accept string " + reg3String
                            + " although is should not have.");
        }
        validator.addNegativeCheck(unionAB.run("#"), "'#' should not be in the union");
    }


}

package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import java.util.List;

public class ConcatinationTests {

    /***
     * this test check if concatination of strings that belong to the first and second regexes domain of the concatination is part of the new concatination domain
     */
    @Test
    public void checkConcatTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkConcat(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


    /***
     * this test check if strings that does  belong to the first and second regex domains of the concatination are NOT part of the new concatination domain
     */
    @Test
    public void checkFirstAndSecondRegexNotInConcat(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkFirstRegexOfConcatNotInConcatination(validator, firstRegex, secondRegex);
            checkSecondRegexOfConcatNotInConcatination(validator, firstRegex, secondRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the concat is the empty regex
     * this test check if strings that belong to the first regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkSecondOfconcatIsEmptyTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getEmptyRegex();
            Automaton automatonA = new RegExp(firstRegex.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(secondRegex.getRegex(), RegExp.ALL).toAutomaton();
            Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
            validator.addCheck(concatAB.isEmpty(), "unionAB.isEmpty() return false");
            validator.addCheck(concatAB.getAcceptStates().isEmpty() , "accept states are not empty");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the concat is the empty regex
     * this test check if strings that belong to the first regex domain of the concat are NOT part of the new concat domain
     */
    @Test
    public void checkConcatIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getEmptyRegex();
            checkFirstRegexOfConcatNotInConcatination(validator, reg1, reg2);
            Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
            Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
            validator.addCheck(concatAB.getAcceptStates().isEmpty() , "accept states are not empty");
            validator.addCheck(concatAB.isEmpty(), "the concat should be empty");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the concat is the empty regex
     * this test check if strings that belong to the second regex domain of the concat are NOT part of the new concat domain
     */
    @Test
    public void checkConcatIsEmptyTest3(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getEmptyRegex();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkSecondRegexOfConcatNotInConcatination(validator, firstRegex, secondRegex);
            Automaton automatonA = new RegExp(firstRegex.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(secondRegex.getRegex(), RegExp.ALL).toAutomaton();
            Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
            validator.addCheck(concatAB.getAcceptStates().isEmpty() , "accept states are not empty");
            validator.addCheck(concatAB.isEmpty(), "the concat should be empty");
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }



    /***
     * in this test both regexes in the concat are the empty regex
     * this test check if the concat is empty
     */
    @Test
    public void checkConcatIsEmptyTest(){
        Validator validator = Validator.getValidator();
        RandomRegex firstRegex = RandomRegex.getEmptyRegex();
        RandomRegex secondRegex = RandomRegex.getEmptyRegex();
        Automaton automatonA = new RegExp(firstRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(secondRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
        validator.addCheck(concatAB.getAcceptStates().isEmpty() , "accept states are not empty");
        validator.addCheck(concatAB.isEmpty(), "the concat should be empty");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


    /***
     * in this test the first regex in the concat is a singleton regex
     * this test check if strings that belong to the complement of the regex domain of the concat are NOT part of the new concat domain
     */
    @Test
    public void checkFirstOfConcatIsSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            RandomRegex reg2 = RandomRegex.getRandRegex();
            checkFirstRegexOfConcatNotInConcatination(validator, reg1, reg2);
            checkSecondRegexOfConcatNotInConcatination(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the concat is a singleton regex
     * this test check if strings that belong to the complement of the regex domain of the concat are NOT part of the new concat domain
     */
    @Test
    public void checkSecondOfConcatIsSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getRandRegexSingleton();
            checkFirstRegexOfConcatNotInConcatination(validator, reg1, reg2);
            checkSecondRegexOfConcatNotInConcatination(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the one of the regexes in the concat is a singleton regex
     * this test check if strings that belong to the regex domain of the concat are part of the new concat domain
     */
    @Test
    public void checkFirstOfConcatIsSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkConcat(validator, firstRegex, secondRegex);
            checkConcat(validator, secondRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the concat are a singleton regex
     * this test check if strings that belong to the regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkBothRegexesOfConcatAreSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            RandomRegex secondRegex = RandomRegex.getRandRegexSingleton();
            checkConcat(validator, firstRegex, secondRegex);
            checkConcat(validator, secondRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the  both regexes in the concat is singleton regex
     * this test check if strings that belong to the complement of the regex domain of the concat are NOT part of the new concat domain
     */
    @Test
    public void checkBothRegexesOfConcatAreSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            RandomRegex reg2 = RandomRegex.getRandRegexSingleton();
            checkFirstRegexOfConcatNotInConcatination(validator, reg1, reg2);
            checkSecondRegexOfConcatNotInConcatination(validator, reg1, reg2);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the concat is singleton regex
     * this test check if strings that belong to the regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkBothRegexesOfConcatAreTheSameSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
            checkConcat(validator, firstRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the concat is a singleton regex
     * this test check if strings that belong to the complement of the regex domain of the concat is NOT part of the new concat domain
     */
    @Test
    public void checkBothRegexesOfConcatAreTheSameSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            checkFirstRegexOfConcatNotInConcatination(validator, reg1, reg1);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


    //****************************************************************************************************************//

    /***
     * in this test the second regex in the concat is the empty string regex
     * this test check if strings that belong to the first regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkSecondOfConcatIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegex();
            RandomRegex secondRegex = RandomRegex.getEmptyStringRegex();
            checkConcat(validator, firstRegex, secondRegex);
            checkConcat(validator, secondRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the concat is the empty string regex
     * this test check if the concat equals to the first automaton
     */
    @Test
    public void checkSecondOfconcatIsEmptyStringTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getEmptyStringRegex();
            Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
            Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
            validator.addCheck(concatAB.equals(automatonA), "the concat should be the same as automatonA\n" +
                                automatonA.toString() + "\n" +
                                concatAB.toString());
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the concat is the empty string regex
     * this test check if the concat equals to the second automaton
     */
    @Test
    public void checkFirstOfconcatIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex reg1 = RandomRegex.getEmptyStringRegex();
            RandomRegex reg2 = RandomRegex.getRandRegex();
            Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
            Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
            validator.addCheck(concatAB.equals(automatonB), "the concat should be the same as automatonB\n" +
                    automatonB.toString() + "\n" +
                    concatAB.toString());
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }



    /***
     * in this test both regexes in the concat are the empty string regex
     * this test check if the concat is equal to both regexes
     */
    @Test
    public void checkconcatIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        RandomRegex reg1 = RandomRegex.getEmptyStringRegex();
        RandomRegex reg2 = RandomRegex.getEmptyStringRegex();
        Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
        Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
        validator.addCheck(concatAB.equals(automatonA),
                "the concat should be the same as automatonA\n" +
                automatonA.toString() + "\n" +
                concatAB.toString());

        validator.addCheck(concatAB.equals(automatonB),
                "the concat should be the same as automatonB\n" +
                automatonB.toString() + "\n" +
                concatAB.toString());
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test both regexes in the concat are the empty string regex
     * this test check if the empty string is in the concat
     */
    @Test
    public void checkconcatIsEmptyStringTest2(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
        validator.addCheck(concatAB.run(""), "concatAB.run(\"\") return false");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

//***********************************************************************************************************


    /***
     * in this test the first regex in the concat contains the empty string
     * this test check if strings that belong to the regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkFirstOfconcatContainsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexWithEmptyString();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkConcat(validator, firstRegex, secondRegex);
            checkConcat(validator, secondRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the concat contains the empty string
     * this test check if strings that belong to the regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkSecondOfconcatContainsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i){
            RandomRegex firstRegex = RandomRegex.getRandRegexWithEmptyString();
            RandomRegex secondRegex = RandomRegex.getRandRegex();
            checkConcat(validator, firstRegex, secondRegex);
            checkConcat(validator, secondRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the concat contains the empty string
     * this test check if strings that belong to the regex domain of the concat is part of the new concat domain
     */
    @Test
    public void checkBothRegexesOfconcatContainsEmptyStringTest() {
        Validator validator = Validator.getValidator();
        for (int i = 0; i < 1000; ++i) {
            RandomRegex firstRegex = RandomRegex.getRandRegexWithEmptyString();
            RandomRegex secondRegex = RandomRegex.getRandRegexWithEmptyString();
            checkConcat(validator, firstRegex, secondRegex);
            checkConcat(validator, secondRegex, firstRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkConcat(Validator validator, RandomRegex reg1, RandomRegex reg2){
        Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
        Automaton concatAB = BasicOperations.concatenate(automatonA, automatonB);
        List<String> firstStrings = reg1.getStrings();
        List<String> secondStrings = reg2.getStrings();
        for (String first : firstStrings) {
            for (String second : secondStrings) {
                System.out.println("regex: " + reg1.getRegex() + " concat " + reg2.getRegex() + "\n" +
                        "string from first regex domain: " + first + "\n" +
                        "string from second regex domain: " + second + "\n");
                validator.addCheck(concatAB.run(first + second),
                        "regex: " + reg1.getRegex() + " concat " + reg2.getRegex() + " did not accept string " + first + second
                                + " although is should have.");
            }

        }
    }

    private void checkFirstRegexOfConcatNotInConcatination(Validator validator, RandomRegex reg1, RandomRegex reg2){
        Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonMiddle = new RegExp("*", RegExp.ALL).toAutomaton();
        Automaton concatAB = BasicOperations.concatenate(automatonA, automatonMiddle).concatenate(automatonB);
        for (String first : reg1.getStrings()) {
            first = getRandStringNotInAutomaton(first, automatonB, true);
            System.out.println("regex: " + reg1.getRegex() + " concat * concat" + reg2.getRegex() + "\n" +
                    "random string not in concat domain: " + first + "\n");
            validator.addNegativeCheck(concatAB.run(first),
                    "regex: " + reg1.getRegex() + " concat * concat" + reg2.getRegex() + " accept string " + first
                            + " although is should not have.");
        }
        validator.addNegativeCheck(concatAB.run("#"), "'#' should not be in the concat");
    }

    private void checkSecondRegexOfConcatNotInConcatination(Validator validator, RandomRegex reg1, RandomRegex reg2){
        Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonMiddle = new RegExp("*", RegExp.ALL).toAutomaton();
        Automaton concatAB = BasicOperations.concatenate(automatonA, automatonMiddle).concatenate(automatonB);
        for (String second : reg2.getStrings()) {
            second = getRandStringNotInAutomaton(second, automatonA, false);
            System.out.println("regex: " + reg1.getRegex() + " concat * concat" + reg2.getRegex() + "\n" +
                    "random string not in concat domain: " + second + "\n");
            validator.addNegativeCheck(concatAB.run(second),
                    "regex: " + reg1.getRegex() + " concat * concat" + reg2.getRegex() + " accept string " + second
                            + " although is should not have.");
        }
        validator.addNegativeCheck(concatAB.run("#"), "'#' should not be in the concat");
    }
    
    private String getRandStringNotInAutomaton(String string, Automaton automaton , boolean concatToEnd){
        String rand = "";
        while (automaton.run(string) ||
                automaton.run(rand) ||
                automaton.run((concatToEnd ? string + rand : rand + string))){
            rand = RandomRegex.getRandString();
            if (concatToEnd) string = string + rand;
            else string = RandomRegex.getRandString() + string;
        }
        return string;
    }


}

package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.BasicOperationsForTests;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import static test.java.bricsTests.BricsTestsSuite.NUM_OF_RANDOMS;

public class IntersectionTests {

    /***
     * this test check if strings that belong to the regex domain of the intersection are part of the new intersection domain
     * this test check if strings that don't belong to the regex domain of the intersection are not part of the new intersection domain
     */
    @Test
    public void checkFirstOfUnionTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex firstRegex;
            RandomRegex secondRegex;
            RandomRegex sharedRegex;
            do {
                firstRegex = RandomRegex.getRandRegexSingleton();
                secondRegex = RandomRegex.getRandRegexSingleton();
                sharedRegex = RandomRegex.getRandRegex();
            } while (isRegexInOtherRegex(firstRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, firstRegex));

            checkIntersection(validator, firstRegex, secondRegex, sharedRegex);
            checkUnionNotInIntersection(validator, firstRegex, secondRegex, sharedRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the intersection is the empty regex
     * this test check if intersection.isEmpty == true
     */
    @Test
    public void checksecondOfIntersectionIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegex();
            RandomRegex reg2 = RandomRegex.getEmptyRegex();
            Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
            Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
            validator.addCheck(intersectionAB.isEmpty(), "intersectionAB.isEmpty() return false");
            validator.addCheck(intersectionAB.getAcceptStates().isEmpty() , "accept states are not empty");
            Assert.assertTrue(validator.getMessage(), validator.isValid());        }
    }

    /***
     * in this test the first regex in the intersection is the empty regex
     * this test check if intersection.isEmpty == true
     */
    @Test
    public void checkFirstOfIntersectionIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            RandomRegex reg1 = RandomRegex.getEmptyRegex();
            RandomRegex reg2 = RandomRegex.getRandRegex();
            Automaton automatonA = new RegExp(reg1.getRegex(), RegExp.ALL).toAutomaton();
            Automaton automatonB = new RegExp(reg2.getRegex(), RegExp.ALL).toAutomaton();
            Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
            validator.addCheck(intersectionAB.isEmpty(), "intersectionAB.isEmpty() return false");
            validator.addCheck(intersectionAB.getAcceptStates().isEmpty() , "accept states are not empty");
            Assert.assertTrue(validator.getMessage(), validator.isValid());        }
    }


    /***
     * in this test both regexes in the intersection are the empty regex
     * this test check if intersection.isEmpty == true
     */
    @Test
    public void checkIntersectionIsEmptyTest2(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = new RegExp("#", RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp("#", RegExp.ALL).toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        validator.addCheck(intersectionAB.isEmpty(), "intersectionAB.isEmpty() return false");
        validator.addCheck(intersectionAB.getAcceptStates().isEmpty() , "accept states are not empty");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }






    /***
     * in this test the both regexes in the intersection are different singleton regexes
     * this test check if the intersection is empty
     */
    @Test
    public void checkRegexesOfIntersectionAreDifferentSingletonsTest(){
        Validator validator = Validator.getValidator();
        RandomRegex firstRegex;
        RandomRegex secondRegex;
        do {
            firstRegex = RandomRegex.getRandRegexSingleton();
            secondRegex = RandomRegex.getRandRegexSingleton();
        } while (isRegexInOtherRegex(firstRegex, secondRegex));
        Automaton automatonA = new RegExp(firstRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(secondRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        validator.addCheck(intersectionAB.isEmpty(),
                "intersectionAB is not empty.\n" +
                "first regex: " + firstRegex.getRegex() + "\n" +
                "second regex: " + secondRegex.getRegex());
        validator.addCheck(intersectionAB.getAcceptStates().isEmpty() , "accept states are not empty");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


    /***
     * in this test first regex in the intersection is singleton regexes and the other contains is
     * this test check if the intersection is the singleton
     */
    @Test
    public void checkRegexInIntersectionContainSingletonsTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex sharedRegex ;
            RandomRegex secondRegex;
            do {
                sharedRegex = RandomRegex.getRandRegexSingleton();
                secondRegex = RandomRegex.getRandRegex();
            } while (isRegexInOtherRegex(sharedRegex, secondRegex));

            checkIntersection(validator, RandomRegex.getEmptyRegex(), secondRegex, sharedRegex);
            checkUnionNotInIntersection(validator, RandomRegex.getEmptyRegex(), secondRegex, sharedRegex);
            checkAutoEqualToIntersection(validator, RandomRegex.getEmptyRegex(), secondRegex, sharedRegex, true);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test second regex in the intersection is singleton regexes and the other contains is
     * this test check if the intersection is the singleton
     */
    @Test
    public void checkRegexInIntersectionContainSingletonsTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex sharedRegex ;
            RandomRegex firstRegex;
            do {
                sharedRegex = RandomRegex.getRandRegexSingleton();
                firstRegex = RandomRegex.getRandRegex();
            } while (isRegexInOtherRegex(sharedRegex, firstRegex));

            checkIntersection(validator, firstRegex, RandomRegex.getEmptyRegex(), sharedRegex);
            checkUnionNotInIntersection(validator, firstRegex, RandomRegex.getEmptyRegex(), sharedRegex);
            checkAutoEqualToIntersection(validator, firstRegex, RandomRegex.getEmptyRegex(), sharedRegex, false);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the intersection is singleton regex
     * this test check if the intersection is equal to the regex
     */
    @Test
    public void checkBothRegexesOfIntersectionAreTheSameSingletonTest(){
        Validator validator = Validator.getValidator();
        RandomRegex firstRegex = RandomRegex.getRandRegexSingleton();
        Automaton automatonA = new RegExp(firstRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp(firstRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        validator.addCheck(intersectionAB.equals(automatonA), "intersectionAB is not equal to automatonA");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the both regexes in the intersection is a singleton regex
     * this test check if strings that belong to the intersection are part of the intersection domain
     */
    @Test
    public void checkBothRegexesOfIntersectionAreTheSameSingletonTest2(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            RandomRegex reg1 = RandomRegex.getRandRegexSingleton();
            checkIntersection(validator, reg1, reg1, reg1);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


//    //****************************************************************************************************************//
//



    /***
     * in this test both regexes in the union are the empty string regex
     * this test check if the intersection is empty
     */
    @Test
    public void checkIntersectionIsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        Automaton automatonA = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton automatonB = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        validator.addCheck(intersectionAB.equals(automatonA), "intersectionAB is not equal to automatonA");
        validator.addCheck(intersectionAB.run(""), "intersectionAB does not accept the empty string");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the second regex in the intersection contains the empty string
     * this test check if strings that belong to the regex domain of the intersection are part of the new intersection domain
     * this test check if strings that don't belong to the regex domain of the intersection are not part of the new intersection domain
     */
    @Test
    public void checkSecondOfIntersectionContainsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            RandomRegex firstRegex;
            RandomRegex secondRegex;
            RandomRegex sharedRegex;
            do {
                firstRegex = RandomRegex.getRandRegex();
                secondRegex = RandomRegex.getRandSingletonWithEmptyString();
                sharedRegex = RandomRegex.getRandRegex();
            } while (isRegexInOtherRegex(firstRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, firstRegex));

            checkIntersection(validator, firstRegex, secondRegex, sharedRegex);
            checkUnionNotInIntersection(validator, firstRegex, secondRegex, sharedRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the first regex in the intersection contains the empty string
     * this test check if strings that belong to the regex domain of the intersection are part of the new intersection domain
     * this test check if strings that don't belong to the regex domain of the intersection are not part of the new intersection domain
     */
    @Test
    public void checkFirstOfIntersectionContainsEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i){
            RandomRegex firstRegex;
            RandomRegex secondRegex;
            RandomRegex sharedRegex;
            do {
                firstRegex = RandomRegex.getRandSingletonWithEmptyString();
                secondRegex = RandomRegex.getRandRegex();
                sharedRegex = RandomRegex.getRandRegex();
            } while (isRegexInOtherRegex(firstRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, firstRegex));

            checkIntersection(validator, firstRegex, secondRegex, sharedRegex);
            checkUnionNotInIntersection(validator, firstRegex, secondRegex, sharedRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * in this test the intersection contains the empty string
     * this test check if strings that belong to the regex domain of the intersection are part of the new intersection domain
     * this test check if strings that don't belong to the regex domain of the intersection are not part of the new intersection domain
     */
    @Test
    public void checkBothRegexesOfIntersectionContainsEmptyStringTest() {
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex firstRegex;
            RandomRegex secondRegex;
            RandomRegex sharedRegex;
            do {
                firstRegex = RandomRegex.getRandRegexSingleton();
                secondRegex = RandomRegex.getRandRegexSingleton();
                sharedRegex = RandomRegex.getRandRegexWithEmptyString();
            } while (isRegexInOtherRegex(firstRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, secondRegex) ||
                    isRegexInOtherRegex(sharedRegex, firstRegex));

            checkIntersection(validator, firstRegex, secondRegex, sharedRegex);
            checkUnionNotInIntersection(validator, firstRegex, secondRegex, sharedRegex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkIntersection(Validator validator, RandomRegex reg1, RandomRegex reg2, RandomRegex sharedRegex){
        RegExp union1 = unionRegexes(reg1.getRegex(), sharedRegex.getRegex());
        RegExp union2 = unionRegexes(reg2.getRegex(), sharedRegex.getRegex());
        Automaton automatonA = union1.toAutomaton();
        Automaton automatonB = union2.toAutomaton();
        Automaton automatonShared = new RegExp(sharedRegex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        validator.addCheck(intersectionAB.equals(automatonShared),
                "intersection of automatonA:\n" +
                        union1 + "\n" +
                "and of automatonB:\n" +
                        union2 + "\n" +
                "is not as expected");
        for (String sharedString : sharedRegex.getStrings()) {
            System.out.println("regex: " + union1 + " intersection with " + union2 + "\n" +
                    "random string from shared regex domain: " + sharedString + "\n");
            validator.addCheck(intersectionAB.run(sharedString),
                    "regex: " + union1 + " intersection with " + union2 + " did not accept string " + sharedString
                            + " although it should have.");
        }
    }

    private void checkUnionNotInIntersection(Validator validator, RandomRegex reg1, RandomRegex reg2, RandomRegex sharedRegex){
        RegExp union1 = unionRegexes(reg1.getRegex(), sharedRegex.getRegex());
        RegExp union2 = unionRegexes(reg2.getRegex(), sharedRegex.getRegex());
        Automaton automatonA = union1.toAutomaton();
        Automaton automatonB = union2.toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        for (String string1 : reg1.getStrings()) {
            System.out.println("regex: " + union1 + " intersection with " + union2 + "\n" +
                    "random string from first regex domain: " + string1 + "\n");
            validator.addNegativeCheck(intersectionAB.run(string1),
                    "regex: " + union1 + " intersection with " + union2 + " accept string " + string1
                            + " although is should not have.");
        }

        for (String string2 : reg2.getStrings()) {
            System.out.println("regex: " + union1 + " intersection with " + union2 + "\n" +
                    "random string from second regex domain: " + string2 + "\n");
            validator.addNegativeCheck(intersectionAB.run(string2),
                    "regex: " + union1 + " intersection with " + union2 + " accept string " + string2
                            + " although is should not have.");
        }
        validator.addNegativeCheck(intersectionAB.run("#"), "'#' should not be in the union");
    }

    private void checkAutoEqualToIntersection (Validator validator, RandomRegex reg1, RandomRegex reg2, RandomRegex sharedRegex, boolean first){
        RegExp union1 = unionRegexes(reg1.getRegex(), sharedRegex.getRegex());
        RegExp union2 = unionRegexes(reg2.getRegex(), sharedRegex.getRegex());
        Automaton automatonA = union1.toAutomaton();
        Automaton automatonB = union2.toAutomaton();
        Automaton intersectionAB = BasicOperationsForTests.intersection(automatonA, automatonB);
        if (first) validator.addCheck(automatonA.equals(intersectionAB),
                union1.toString() + " is not equal to " + union1.toString() + "intersect with " + union2.toString());
        else validator.addCheck(automatonB.equals(intersectionAB),
                union2.toString() + " is not equal to " + union1.toString() + "intersect with " + union2.toString());
    }

    private boolean isRegexInOtherRegex(RandomRegex regex1, RandomRegex regex2){
        for (String string : regex1.getStrings()) {
            if (regex2.getStrings().contains(string)) return true;
        }
        for (String string : regex2.getStrings()) {
            if (regex1.getStrings().contains(string)) return true;
        }
        return false;
    }

    private RegExp unionRegexes(String reg1, String reg2){
        String newReg;
        if (reg1.equals(reg2)) newReg = reg1;
        else if (reg1.equals("#")) newReg = reg2;
        else if (reg2.equals("#")) newReg = reg1;
        else newReg = reg1 + "|" + reg2;
        return new RegExp(newReg, RegExp.ALL);
    }
}

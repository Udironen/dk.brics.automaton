package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ComplementTests {

    private final static int NUM_OF_RANDOMS = 1;

    /***
     * this test checks that complement of a random regex behaves as expected
     */
    @Test
    public void complementOfRandRegexTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex regex = RandomRegex.getRandRegex();
            checkComplement(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that complement of a random singleton regex behaves as expected
     */
    @Test
    public void complementOfSingletonTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex regex = RandomRegex.getRandRegexSingleton();
            checkComplement(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that complement of a random regex with the empty word behaves as expected
     */
    @Test
    public void complementOfRegexWithEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex regex = RandomRegex.getRandRegexWithEmptyString();
            checkComplement(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that complement of a random singleton regex with the empty word behaves as expected
     */
    @Test
    public void complementOfSingletonWithEmptyStringTest(){
        Validator validator = Validator.getValidator();
        for (int i = 0; i < NUM_OF_RANDOMS; ++i) {
            RandomRegex regex = RandomRegex.getRandSingletonWithEmptyString();
            checkComplement(validator, regex);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that complement of the empty word behaves as expected
     */
    @Test
    public void complementOfEmptyStringTest(){
        Validator validator = Validator.getValidator();
        RandomRegex regex = RandomRegex.getEmptyStringRegex();
        checkComplement(validator, regex);
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that complement of the empty regex behaves as expected
     */
    @Test
    public void complementOfEmptyRegexTest(){
        Validator validator = Validator.getValidator();
        RandomRegex regex = RandomRegex.getEmptyRegex();
        checkComplement(validator, regex);
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that complement of the total regex behaves as expected
     */
    @Test
    public void complementOfTotalRegexTest(){
        Validator validator = Validator.getValidator();
        RandomRegex regex = RandomRegex.getTotalRegex();
        checkComplement(validator, regex);
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    private void checkComplement(Validator validator, RandomRegex regex){

        checkStringInAutoOrInCompl(validator, regex);

        checkAutoEmptyOrTotal(validator, regex);

        checkUnionWithCompleIsTotal(validator, regex);

        checkComplOfUnionWithComplIsEmpty(validator, regex);
    }

    private void checkStringInAutoOrInCompl(Validator validator, RandomRegex regex) {
        Automaton automatonA = new RegExp(regex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton complimentA = automatonA.complement();

        for (String str : RandomRegex.getRandomStrings(1000)) {
            if (automatonA.run(str))
                validator.addNegativeCheck(complimentA.run(str),
                        "complement of " + regex.getRegex() + " accept " + str + " although it shouldn't have");
            else if (complimentA.run(str))
                validator.addCheck(complimentA.run(str),
                        "complement of " + regex.getRegex() + " did not accept " + str + " although it should have");
            else validator.addCheck(false, "string " + str + " is not in " + regex.getRegex() + " and not in its complemnt");
        }
    }

    private void checkAutoEmptyOrTotal(Validator validator, RandomRegex regex){
        Automaton automatonA = new RegExp(regex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton complimentA = automatonA.complement();
        if (automatonA.isEmpty()) validator.addCheck(complimentA.isTotal(), "complement of the empty regex is not total");
        else if (automatonA.isTotal()) validator.addCheck(complimentA.isEmpty(), "complement of the total regex is not empty");

    }

    private void checkUnionWithCompleIsTotal(Validator validator, RandomRegex regex){
        Automaton automatonA = new RegExp(regex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton complimentA = automatonA.complement();
        Automaton unionOfComplements = complimentA.union(automatonA);
        String strNotInUnion = unionOfComplements.getShortestExample(false);
        validator.addCheck(unionOfComplements.isTotal(),
                "union of " + regex.getRegex() + " and its complement isTotal() == false\n");
        validator.addCheck(isNull(strNotInUnion),
                "union of " + regex.getRegex() + " and its complement getShortestExample(false) == " + strNotInUnion);
        if (nonNull(strNotInUnion))
                validator.addCheck(unionOfComplements.run(strNotInUnion),
                        "unionOfComplements.run(" + strNotInUnion + ") returned false");

    }

    private void checkComplOfUnionWithComplIsEmpty(Validator validator, RandomRegex regex){
        Automaton automatonA = new RegExp(regex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton complimentA = automatonA.complement();
        Automaton unionOfComplements = complimentA.union(automatonA);
        Automaton complOfUnion = unionOfComplements.complement();
        validator.addCheck(complOfUnion.isEmpty(),
                "complement of union of " + regex.getRegex() + " and its complement is not empty\n" +
                        "string in the automaton: " + complOfUnion.getShortestExample(true));

    }
}

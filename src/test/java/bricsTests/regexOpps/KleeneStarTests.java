package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import java.util.ArrayList;
import java.util.List;

import static test.java.bricsTests.BricsTestsSuite.NUM_OF_RANDOMS;

public class KleeneStarTests {

    /***
     * this test checks that Kleene Star on a random regex behaves as expected
     */
    @Test
    public void kleeneStarTest() {
        Validator validator = Validator.getValidator();
        for (int j = 0; j < NUM_OF_RANDOMS; ++j)
        {
            RandomRegex regex1 = RandomRegex.getRandRegex();
            checkKleeneStar(validator, regex1);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that Kleene Star on a random regex with the empty string behaves as expected
     */
    @Test
    public void kleeneStarWithEmptyStringTest() {
        Validator validator = Validator.getValidator();
        for (int j = 0; j < NUM_OF_RANDOMS; ++j)
        {
            RandomRegex regex1 = RandomRegex.getRandRegexWithEmptyString();
            checkKleeneStar(validator, regex1);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that Kleene Star on a random singleton regex behaves as expected
     */
    @Test
    public void kleeneStarOfSingletonTest() {
        Validator validator = Validator.getValidator();
        for (int j = 0; j < NUM_OF_RANDOMS; ++j)
        {
            RandomRegex regex1 = RandomRegex.getRandRegexSingleton();
            checkKleeneStar(validator, regex1);
        }
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that Kleene Star on the empty string equals to the empty string
     */
    @Test
    public void kleeneStarOfEmptyStringTest() {
        Validator validator = Validator.getValidator();
        Automaton emptyString = new RegExp("()", RegExp.ALL).toAutomaton();
        Automaton kleeneStar = emptyString.repeat();
        validator.addCheck(kleeneStar.equals(emptyString), "kleene star of empty string is not equal to the empty string regex")
                .addCheck(kleeneStar.run(""), "kleene star of empty string does not accept empty string");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

    /***
     * this test checks that Kleene Star on the empty regex equals to the empty string regex
     */
    @Test
    public void kleeneStarOfEmptyRegexTest() {
        Validator validator = Validator.getValidator();
        Automaton empty = new RegExp("#", RegExp.ALL).toAutomaton();
        Automaton kleeneStar = empty.repeat();
        Automaton emptyString = new RegExp("()", RegExp.ALL).toAutomaton();
        validator.addCheck(kleeneStar.equals(emptyString), "kleene star of empty regex is not equal to the empty string regex")
                .addCheck(kleeneStar.run(""), "kleene star of empty regex does not accept empty string")
                .addNegativeCheck(kleeneStar.isEmpty(), "kleeneStar.isEmpty() == true although it should be false")
                .addNegativeCheck(kleeneStar.getAcceptStates().isEmpty(), "kleene star of empty regex does not have an accepting state");
        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }


    private void checkKleeneStar(Validator validator, RandomRegex regex1){
        List<String> strings = new ArrayList<>(regex1.getStrings());
        for (int i = 0 ; i < 5 ; ++i) {
            StringBuilder sb = new StringBuilder();
            for (String s : strings) {
                sb.append(s);
            }
            strings.add(sb.toString());
        }
        strings.add("");
        Automaton kleeneStar = new RegExp(regex1.getRegex()).toAutomaton().repeat();
        System.out.println("regex: " + regex1.getRegex());
        for (int i = 0; i < strings.size(); ++i) {
            System.out.println("strings in the index " + i + " : " + strings.get(i));
            validator.addCheck(kleeneStar.run(strings.get(i)),
                    "kleeneStar of " + regex1.getRegex() + " did not accept " + strings.get(i));
        }
    }
}

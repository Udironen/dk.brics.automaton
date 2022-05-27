package test.java.bricsTests.regexOpps;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import org.junit.Assert;
import org.junit.Test;
import test.java.bricsTests.RandomRegex;
import test.java.bricsTests.Validator;

import java.util.List;

public class ComplementTests {


    @Test
    public void complementTest(){
        Validator validator = Validator.getValidator();
        RandomRegex regex = RandomRegex.getRandRegex();
        Automaton automatonA = new RegExp(regex.getRegex(), RegExp.ALL).toAutomaton();
        Automaton complimentA = automatonA.complement();

        for (String str : RandomRegex.getRandomStrings(100)) {
            if (automatonA.run(str))
                validator.addNegativeCheck(complimentA.run(str),
                        "complement of " + regex.getRegex() + " accept " + str + " although it shouldn't have");
            else if (complimentA.run(str))
                validator.addCheck(complimentA.run(str),
                        "complement of " + regex.getRegex() + " did not accept " + str + " although it should have");
            else validator.addCheck(false, "string " + str + " is not in " + regex.getRegex() + " and not in its complemnt");
        }
        if (automatonA.isEmpty()) validator.addCheck(complimentA.isTotal(), "complement of the empty regex is not total");
        else if (automatonA.isTotal()) validator.addCheck(complimentA.isEmpty(), "complement of the total regex is not empty");

        Automaton unionOfComplements = complimentA.union(automatonA);
        validator.addCheck(unionOfComplements.isTotal(), "union of " + regex.getRegex() + " and its complement is not total\n" +
                "string not in the automaton: " + unionOfComplements.getShortestExample(false));

        Automaton complOfUnion = unionOfComplements.complement();
        validator.addCheck(complOfUnion.isEmpty(),
                "complement of union of " + regex.getRegex() + " and its complement is not empty\n" +
                "string in the automaton: " + complOfUnion.getShortestExample(true));


        Assert.assertTrue(validator.getMessage(), validator.isValid());
    }

}

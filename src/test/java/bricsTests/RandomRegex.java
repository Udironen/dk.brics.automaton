package test.java.bricsTests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomRegex {
    private String regex;
    private List<String> strings;

    private RandomRegex(String regex, List<String> strings){
        this.regex = regex;
        this.strings = strings;
    }

    public static String getRandString(){
        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        alphabet = alphabet.concat(alphabet.toLowerCase());
        alphabet = alphabet.concat("1234567890");


        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = random.nextInt(10);

        for(int i = 0; i < length + 1; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static char getRandBinarSign(){
        String alphabet = "|q";
        Random random = new Random();
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }
    public static char getRandUnarSign(){
        String alphabet = "+q";
        Random random = new Random();
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }

    public static RandomRegex getRandRegexSingleton(){
        String randString = getRandString();
        List<String> strings = new ArrayList<>();
        strings.add(randString);
        return new RandomRegex(randString, strings);
    }

    public static RandomRegex getRandRegex(){
        List<String> genStrings = new ArrayList<>();
        ArrayList<String> tempStrings = new ArrayList<>();
        boolean toConcat = false;
        Random random = new Random();
        int numOfSingles = random.nextInt(3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfSingles; ++i){
            String randString = getRandString();
            if (!toConcat){
                genStrings.addAll(tempStrings);
                tempStrings = new ArrayList<>();
                tempStrings.add(randString);
            }
            else {
                for (int j = 0; j < tempStrings.size(); ++j) {
                    tempStrings.set(j, tempStrings.get(j).concat(randString));
                }
            }
            sb.append("((").append(randString).append(")");
            char unarSign = getRandUnarSign();
            if (unarSign != 'q') {
                sb.append(unarSign);
                for (int j = 1; j < 5; ++j) {
                    if (!toConcat){
                        tempStrings.add(randString.repeat(j+1));
                    }
                    else {
                        if (tempStrings.size() <= j) {
                            continue;
                        }
                        tempStrings.set(j, tempStrings.get(j).concat(randString.repeat(j + 1)));
                    }
                }
            }
            sb.append(")");
            char binarSign = getRandBinarSign();
            if (binarSign != 'q') {
                toConcat = false;
                sb.append(binarSign);
            }
            else toConcat = true;
        }
        String randString = getRandString();
        if (!toConcat){
            genStrings.addAll(tempStrings);
            tempStrings = new ArrayList<>();
            tempStrings.add(randString);
        }
        else {
            for (int j = 0; j < tempStrings.size(); ++j) {
                tempStrings.set(j, tempStrings.get(j).concat(randString));
            }
        }
        sb.append("(").append(randString).append(")");
        char unarSign = getRandUnarSign();
        if (unarSign != 'q') {
            sb.append(unarSign);
            for (int j = 1; j < 5; ++j) {
                if (!toConcat){
                    tempStrings.add(randString.repeat(j+1));
                }
                else {
                    if (tempStrings.size() <= j) {
                       continue;
                    }
                    tempStrings.set(j, tempStrings.get(j).concat(randString.repeat(j + 1)));
                }
            }
        }
        genStrings.addAll(tempStrings);

        return new RandomRegex(sb.toString(), genStrings);
    }

    public String getRegex() {
        return regex;
    }

    public List<String> getStrings() {
        return strings;
    }

    public static RandomRegex getEmptyRegex(){
        return new RandomRegex("#", new ArrayList<>());
    }

    public static RandomRegex getTotalRegex(){
        return new RandomRegex("@", new ArrayList<>());
    }

    public static RandomRegex getEmptyStringRegex(){
        return new RandomRegex("()", Collections.singletonList(""));
    }

    public static RandomRegex getRandRegexWithEmptyString(){
        RandomRegex randomRegex = getRandRegex();
        randomRegex.regex = randomRegex.regex.concat("|()");
        randomRegex.strings.add("");
        return randomRegex;
    }

    public static RandomRegex getRandSingletonWithEmptyString(){
        RandomRegex randomRegex = getRandRegexSingleton();
        randomRegex.regex = randomRegex.regex.concat("|()");
        randomRegex.strings.add("");
        return randomRegex;
    }

    public static List<String> getRandomStrings(int num){
        List<String> strings = new ArrayList<>();
        for (int i = 0 ; i < num ; ++i) {
            strings.add(getRandString());
        }
        return strings;
    }


}

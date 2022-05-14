package test.java.bricsTests;

import java.util.Random;

public class RandomRegex {

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

    public static char getRandRegexSign(){
        String alphabet = "|?*+q";
        Random random = new Random();
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }
    public static char getRandUnarSign(){
        String alphabet = "+q";
        Random random = new Random();
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }

    public static String getRandRegEx(){
        Random random = new Random();
        int numOfSingles = random.nextInt(3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfSingles; ++i){
            sb.append("((").append(getRandString()).append(")");
            char sign = getRandUnarSign();
            if (sign != 'q') sb.append(sign);
            sb.append(")|");
        }
        sb.append("(").append(getRandString()).append(")");
        char sign = getRandUnarSign();
        if (sign != 'q') sb.append(sign);

        return sb.toString();
    }
}

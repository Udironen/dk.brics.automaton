package test.java.bricsTests;

import java.util.Objects;

public class Validator {
    boolean valid = true;
    StringBuilder errorMessageBuilder = new StringBuilder();

    public static Validator getValidator() {
        return new Validator();
    }

    public Validator addCheck(boolean check, String errorMessage) {
        valid = valid && check;
        if (!check) errorMessageBuilder.append(errorMessage).append("\n");
        return this;
    }

    public Validator addCheckEquals(Object expected, Object actual, String message) {
        if (Objects.isNull(expected)) {
            return addCheck(Objects.isNull(actual), message +
                    "\n" + "expected: " + null + ", actual: " + actual);
        }
        return addCheck(expected.equals(actual), message +
                "\n" + "expected: " + expected.toString() + ", actual: " + (Objects.isNull(actual) ? null : actual.toString()));
    }

    public Validator addNegativeCheck(boolean check, String errorMessage) {
        return addCheck(!check, errorMessage);
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return errorMessageBuilder.toString();
    }


}



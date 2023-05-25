package dat.backend.model.services;

public class PasswordSecurityCheck {

    /**
     * performs a security check on the password inserted
     * @param password that needs to be checked
     * @return true if the password is valid, otherwise false.
     */
    public static boolean securityCheck(String password) {
        if (password.length() < 6) {
            return false;
        }

        if (letterAndDigitCheck(password) == false) {
            return false;
        }

        if (uppercaseAndLowercaseCheck(password) == false) {
            return false;
        }

        return true;
    }

    /**
     * Checks if right amount of letters and digits
     * @param password that needs to be checked
     * @return true if the right amount of letters and digits is there, otherwise false.
     */
    private static boolean letterAndDigitCheck(String password) {
        char c;
        int countLetters = 0;
        int countDigits = 0;

        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);

            if (Character.isLetter(c)) {
                countLetters += 1;
            }
        }

        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);

            if (Character.isDigit(c)) {
                countDigits += 1;
            }
        }

        if (countLetters < 2 || countDigits < 3) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the right amount of uppercase and lowercase letters is there
     * @param password that needs to be checked
     * @return true if the right amount of lower and upper case letters is there, otherwise false.
     */
    private static boolean uppercaseAndLowercaseCheck(String password) {
        char c;
        int uppercaseCount = 0;
        int lowercaseCount = 0;

        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                lowercaseCount += 1;
            }
            if (Character.isUpperCase(c)) {
                uppercaseCount += 1;
            }
        }

        if (uppercaseCount < 1 || lowercaseCount < 1) {
            return false;
        }
        return true;
    }


}



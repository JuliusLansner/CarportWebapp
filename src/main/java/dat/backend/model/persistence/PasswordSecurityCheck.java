package dat.backend.model.persistence;

public class PasswordSecurityCheck {

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



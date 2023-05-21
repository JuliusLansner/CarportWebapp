package dat.backend.model.services;

import dat.backend.model.services.PasswordSecurityCheck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordSecurityCheckTest {

    @Test
    void correctPassword() {
        assertTrue(PasswordSecurityCheck.securityCheck("6318Ma"));
    }

    @Test
    void incorrectPasswordMissingLowercase() {
        assertFalse(PasswordSecurityCheck.securityCheck("6318AA"));
    }

    @Test
    void incorrectPasswordMissingUppercase() {
        assertFalse(PasswordSecurityCheck.securityCheck("6318aa"));
    }

    @Test
    void incorrectPasswordNotLongEnough() {
        assertFalse(PasswordSecurityCheck.securityCheck("631aA"));
    }

    @Test
    void incorrectPasswordNotEnoughDigits() {
        assertFalse(PasswordSecurityCheck.securityCheck("63aAyhj"));
    }

}
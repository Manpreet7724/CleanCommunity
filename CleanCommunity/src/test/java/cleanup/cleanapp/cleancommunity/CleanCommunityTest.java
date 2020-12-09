package cleanup.cleanapp.cleancommunity;

import org.junit.Test;

import static org.junit.Assert.*;

public class CleanCommunityTest {

    @Test
    public void emailValidation() {
        assertTrue(Login.EmailValidator("name@email.com"));
        assertTrue(Login.EmailValidator("name@email.co.uk"));
        assertFalse(Login.EmailValidator("name@email"));
        assertFalse(Login.EmailValidator("name@email..com"));
        assertFalse(Login.EmailValidator("@email..com"));
        assertFalse(Login.EmailValidator(null));

        assertTrue(SignUp.EmailValidator("name@email.com"));
        assertTrue(SignUp.EmailValidator("name@email.co.uk"));
        assertFalse(SignUp.EmailValidator("name@email"));
        assertFalse(SignUp.EmailValidator("name@email..com"));
        assertFalse(SignUp.EmailValidator(null));
    }


}
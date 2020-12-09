package cleanup.cleanapp.cleancommunity;

import org.junit.Rule;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CleanCommunityTest
{
    @Test
    public void emailValidation()
    {
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

    @Test
    public void passwordValidation()
    {
        assertTrue(Login.PasswordValidator("password1"));
        assertTrue(Login.PasswordValidator("Password1"));
        assertFalse(Login.PasswordValidator("password!"));
        assertFalse(Login.PasswordValidator("password"));
        assertFalse(Login.PasswordValidator("123123123"));
        assertFalse(Login.PasswordValidator("hey"));
        assertFalse(Login.PasswordValidator("hey!"));
        assertFalse(Login.PasswordValidator(null));

        assertTrue(SignUp.PasswordValidator("password1"));
        assertTrue(SignUp.PasswordValidator("Password1"));
        assertFalse(SignUp.PasswordValidator("password!"));
        assertFalse(SignUp.PasswordValidator("password"));
        assertFalse(SignUp.PasswordValidator("123123123"));
        assertFalse(SignUp.PasswordValidator("hey"));
        assertFalse(SignUp.PasswordValidator("hey!"));
        assertFalse(SignUp.PasswordValidator(null));
    }
}
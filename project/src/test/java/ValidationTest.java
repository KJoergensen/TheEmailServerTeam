import Models.User;
import Services.Validator;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hyldgaard on 9/8/15.
 */
public class ValidationTest {

    @Test
    public void validateUserTrueTest() {
        User user = new User();
        user.setUsername("keamailtest@gmail.com");
        user.setPassword("mailtester");

        org.junit.Assert.assertEquals("success", Validator.validateUser(user));

    }

    @Test
    public void validateUserFalseTest() {
        User user = new User();
        user.setUsername("keamailtest@hotmail.com");
        user.setPassword("mailtester");

        org.junit.Assert.assertEquals("Incorrect Credentials", Validator.validateUser(user));

    }

    @Test
    public void validateUserWrongPasswordTest() {
        User user = new User();
        user.setUsername("keamailtest@gmail.com");
        user.setPassword(" ");

        org.junit.Assert.assertEquals("authenticate failed", Validator.validateUser(user));
    }

    @Test
    public void validateNoUserCredentialsTest() {
        User user = new User();
        user.setUsername(" ");
        user.setPassword(" ");

        org.junit.Assert.assertEquals("Incorrect Credentials", Validator.validateUser(user));
    }

    @Test
    public void validateUserNoUsernameTest() {
        User user = new User();
        user.setUsername("");
        user.setPassword("mailtester");

        org.junit.Assert.assertEquals("Incorrect Credentials", Validator.validateUser(user));
    }

    @Test
    public void validateUserOnlyAtGmailInUsernameTest() {
        User user = new User();
        user.setUsername("@gmail.com");
        user.setPassword("mailtester");

        org.junit.Assert.assertEquals("authenticate failed", Validator.validateUser(user));
    }

}

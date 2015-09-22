import Controllers.LoginController;
import org.junit.Test;

/**
 * Created by Hyldgaard on 9/21/15.
 */
public class LoginTest {

    @Test
    public void validateLogin() {
        String username = "keamailtest@gmail.com";
        char[] password = {'m', 'a', 'i', 'l', 't', 'e', 's', 't', 'e', 'r' };

        LoginController loginController = new LoginController();

        org.junit.Assert.assertEquals("success", loginController.validateLogin(username, password));
    }

    @Test
    public void validateLoginNoPassword() {
        String username = "keamailtest@gmail.com";
        char[] password = {};

        LoginController loginController = new LoginController();

        org.junit.Assert.assertEquals("authenticate failed", loginController.validateLogin(username, password));
    }

    @Test
    public void validateLoginNoUsername() {
        String username = "";
        char[] password = {'m', 'a', 'i', 'l', 't', 'e', 's', 't', 'e', 'r' };

        LoginController loginController = new LoginController();

        org.junit.Assert.assertEquals("Incorrect Credentials", loginController.validateLogin(username, password));
    }

    @Test
    public void validateLoginWrongEmailServiceWithCorrectPassword() {
        String username = "keamailtest@hotmail.com";
        char[] password = {'m', 'a', 'i', 'l', 't', 'e', 's', 't', 'e', 'r' };

        LoginController loginController = new LoginController();

        org.junit.Assert.assertEquals("Incorrect Credentials", loginController.validateLogin(username, password));
    }

    @Test
    public void validateLoginCorrectEmailServiceWithWrongPassword() {
        String username = "keamailtest@gmail.com";
        char[] password = {'m', 'a', 'i', 'l', 't', 'e', 's', 't' };

        LoginController loginController = new LoginController();

        org.junit.Assert.assertEquals("authenticate failed", loginController.validateLogin(username, password));
    }

}

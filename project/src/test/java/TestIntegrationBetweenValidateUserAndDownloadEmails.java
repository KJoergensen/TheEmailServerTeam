import Models.User;
import Utilities.EmailReceiver;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Hyldgaard on 9/21/15.
 */
public class TestIntegrationBetweenValidateUserAndDownloadEmails {

    @Test
    public void downloadEmailTrueTest() {
        User user = new User();
        user.setUsername("keamailtest@gmail.com");
        user.setPassword("mailtester");

        EmailReceiver receiver = new EmailReceiver();

        org.junit.Assert.assertTrue(!receiver.downloadEmails(user.getUsername(), user.getPassword()).isEmpty());

    }

    @Test
    public void downloadEmailFalseTest() {
        User user = new User();
        user.setUsername("keamailtest@gmail.com");
        user.setPassword("");

        EmailReceiver receiver = new EmailReceiver();

        org.junit.Assert.assertTrue(receiver.downloadEmails(user.getUsername(), user.getPassword()).isEmpty());
    }

}

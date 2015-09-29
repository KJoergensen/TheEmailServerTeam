package Services;

import Models.User;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

/**
 * Created by Kasper on 08-09-2015.
 */
public class Validator
{

    private static boolean validateCorrectEmailEnding(String email) {
        return !email.isEmpty() && email.length() > 9 && email.subSequence(email.length() - 10, email.length()).equals("@gmail.com");
    }

    public static String validateUser(User user)
    {
        if (validateCorrectEmailEnding(user.getUsername()))
        {
            int port = 587;
            String host = "smtp.gmail.com";

            try {
                Properties props = new Properties();
                // required for gmail
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                // or use getDefaultInstance instance if desired...
                Session session = Session.getInstance(props, null);
                Transport transport = session.getTransport("smtp");
                transport.connect(host, port, user.getUsername(), user.getPassword());
                transport.close();
                return "success";

            } catch (AuthenticationFailedException e) {
                e.printStackTrace();
                return "authenticate failed";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "unexpected fail";
            }
        }
        else
        {
            return("Incorrect email address");
        }
    }
}

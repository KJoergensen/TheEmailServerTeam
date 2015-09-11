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
    // TODO: Validate email method


    private static boolean validateCorrectEmailEnding(String email) {
        if (!email.isEmpty()) {
            int emailLength = email.length();
            System.out.println("mail: " + email);
            if (email.subSequence(emailLength-10, emailLength).equals("@gmail.com")) {
                return true;
            } else {
                System.out.println("false? "+ email);
                return false;
            }

        } else {
            return false;
        }
    }

    public static String validateUser(User user) {
        if (validateCorrectEmailEnding(user.getUsername())) {
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
                System.out.println("Login succesfull");
                return "success";

            } catch (AuthenticationFailedException e) {
                System.out.println("Login failed "+ user.getUsername() + " and ");// + pwd);
                e.printStackTrace();
                return "authenticate failed";
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Unknown login failure");
                return "unexpected fail";
            }
        } else {
            System.out.println("You suck!");
            return("You suck at logging in!");
        }


    }
}

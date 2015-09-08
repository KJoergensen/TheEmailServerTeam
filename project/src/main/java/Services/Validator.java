package Services;


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


    public boolean validateCorrectEmailEnding(String email) {
        if (!email.isEmpty()) {
            int emailLength = email.length();
            if (email.subSequence(emailLength-10, emailLength) == "@gmail.com") {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public String validateEmail(String username, String password) {
        if (validateCorrectEmailEnding(username)) {
            int port = 587;
            String host = "smtp.gmail.com";
            String user = username;
            String pwd = password;

            try {
                Properties props = new Properties();
                // required for gmail
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                // or use getDefaultInstance instance if desired...
                Session session = Session.getInstance(props, null);
                Transport transport = session.getTransport("smtp");
                transport.connect(host, port, user, pwd);
                transport.close();
                System.out.println("Login succesfull");
                return "Succes";

            } catch (AuthenticationFailedException e) {
                System.out.println("Login failed");
                e.printStackTrace();
                return "Failed";
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Unknown login failure");
                return "Failed";
            }
        } else {
            System.out.println("You suck!");
            return("You suck at logging in!");
        }


    }
}

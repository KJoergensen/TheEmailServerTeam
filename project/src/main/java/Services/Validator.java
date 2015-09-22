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
                //System.out.println("Login successful");
                LogHandler.addNewRow(user.getUsername(), "Validation successful");
                return "success";

            } catch (AuthenticationFailedException e) {
                //System.out.println("Login failed "+ user.getUsername() + " and ");// + pwd);
                LogHandler.addNewRow(user.getUsername(), "Validation failed");
                e.printStackTrace();
                return "authenticate failed";
            } catch (MessagingException e) {
                //System.out.println("Unknown login failure");
                LogHandler.addNewRow(user.getUsername(), "Validation unexpected failure");
                e.printStackTrace();
                return "unexpected fail";
            }
        }
        else
        {
            //System.out.println("Incorrect email address");
            LogHandler.addNewRow(user.getUsername(), "Validation failed - Incorrect email adress");
            return("Incorrect email address");
        }


    }
}

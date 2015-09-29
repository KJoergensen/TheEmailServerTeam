package Controllers;

import Models.Email;
import Models.User;
import Services.Validator;
import Utilities.EmailReceiver;
import Views.InboxView;
import java.util.ArrayList;

/**
 * Created by Hyldgaard on 9/4/15.
 */
public class LoginController {
    private User user;
    private InboxController inboxController;
    private EmailReceiver emailReceiver;

    public String validateLogin (String email, char[] password)
    {
        System.out.println("Validating user");
        user = new User();
        user.setUsername(email);
        user.setPassword(new String(password));

        return Validator.validateUser(user);
    }

    public void launchInbox ()
    {
        System.out.println("Launching inbox");

        emailReceiver = new EmailReceiver();
        ArrayList<Email> mailList = emailReceiver.downloadEmails(user.getUsername(), user.getPassword());
        inboxController = new InboxController(user, mailList, emailReceiver);

        try {
            new InboxView(inboxController, mailList, user);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

package Controllers;

import Models.User;
import Services.Validator;
import Utilities.EmailReceiver;
import Views.InboxView;

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
        inboxController = new InboxController(user);
        emailReceiver = new EmailReceiver();
        
        try {
            new InboxView(inboxController, emailReceiver.downloadEmails(user.getUsername(), user.getPassword()),user);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

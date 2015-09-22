package Controllers;

import Models.Email;
import Models.User;
import Services.LogHandler;
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
    private InboxView inboxView;
    // Metode der returner et svar fra googles mailserver
    // TODO: Method that adds new line to log (login attempted)
    // TODO: Method that adds new line to log (login completed)
    // TODO: Method that adds new line to log (login denied)

    public String validateLogin (String email, char[] password)
    {
        LogHandler.addNewRow(email, "Validating user");
        System.out.println("Validating user");
        user = new User();
        user.setUsername(email);
        user.setPassword(new String(password));

        return Validator.validateUser(user);
    }

    public void launchInbox ()
    {
        LogHandler.addNewRow(user.getUsername(), "Launching inbox");
        System.out.println("Launching inbox");
        inboxController = new InboxController(user);
        emailReceiver = new EmailReceiver();

        try {
            inboxView = new InboxView(inboxController, emailReceiver.downloadEmails(user.getUsername(), user.getPassword()),user);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

package Controllers;

import Models.Email;
import Utilities.EmailReceiver;
import Utilities.EmailSender;
import java.util.ArrayList;

public class InboxController
{
    public EmailReceiver receiver = new EmailReceiver();
    public EmailSender sender = new EmailSender();
    public ArrayList<Email> inbox = new ArrayList<Email>();


    public void sendEmail(Email email)
    {
        // TODO: Return Http status code
        sender.sendEmail(email);
    }

    public ArrayList<Email> updateInbox()
    {
        // Retrieving all emails from server
        ArrayList<Email> newEmails = receiver.getEmails();
        // Removing all duplicates
        newEmails.removeAll(inbox);

        // Adding new emails to inbox
        for (Email mail : newEmails)
        {
            inbox.add(mail);
        }

        return inbox;
    }

    // TODO: Method that adds new line to log (new email)
    // TODO: Method that adds new line to log (update inbox)
}

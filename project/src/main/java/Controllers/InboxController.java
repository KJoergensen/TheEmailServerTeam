package Controllers;

import Models.Email;
import Models.User;
import Utilities.EmailReceiver;
import Utilities.EmailSender;

import java.util.*;

public class InboxController
{
    private EmailReceiver receiver;
    private EmailSender sender = new EmailSender();
    private ArrayList<Email> inbox;
    private User user;

    public InboxController (User user, ArrayList<Email> inbox, EmailReceiver receiver)
    {
        this.inbox = inbox;
        this.receiver = receiver;
        this.user = user;
    }

    public void sendEmail(String recipient, String subject, String body)
    {
        int id = 0;
        String from = user.getUsername();
        Date date = new Date();
        boolean newEmail = true;

        Email email = new Email(id, recipient, from, subject, body, date, newEmail);
        sender.sendMessage(email, user);
    }

    public ArrayList<Email> updateInbox()
    {
        // Retrieving all emails from server
        ArrayList<Email> newEmails = new ArrayList<>();

        try
        {
            newEmails = receiver.updateInbox();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Adding new emails to inbox
        if (!newEmails.isEmpty())
        {
            try
            {
                for (Email mail : newEmails)
                {
                    inbox.add(mail);
                }

            }
            catch (Exception e)
            {
                System.out.println("Failed to add 0 emails to list");
            }
        }

        return inbox;
    }
}

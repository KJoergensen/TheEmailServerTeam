package Utilities;

import Models.Email;
import Models.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender
{
    public static void sendMessage(Email email, User user)
    {
        // Recipient's email ID needs to be mentioned.
        String to = email.getTo();//change accordingly

        // Sender's email ID needs to be mentioned
        String from = user.getUsername();//change accordingly
        final String username = user.getUsername();//change accordingly
        final String password = user.getPassword();//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try
        {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(email.getSubject());

            // Now set the actual message
            message.setText(email.getBody());

            // Send message
            Transport.send(message);
        }
        catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
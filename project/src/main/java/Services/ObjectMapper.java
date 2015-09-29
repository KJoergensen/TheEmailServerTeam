package Services;

import Models.Email;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.*;


/**
 * Created by Kasper on 14-09-2015.
 */
public class ObjectMapper
{
    public static ArrayList<Email> mapObjects(Message[] messages) throws Exception
    {
        System.out.println("Mapping emails...");
        ArrayList<Email> list = new ArrayList<Email>();

        for (int i = 0; i < messages.length; i++)
        {
            System.out.println("Mapping message " + i);
            Message msg = messages[i];
            Address[] fromAddress = msg.getFrom();
            String from = "";

            // Checking for draft emails
            if (fromAddress.length > 0)
            {
                from = fromAddress[0].toString();
            }

            String subject = msg.getSubject();
            Date sentDate = msg.getSentDate();
            String messageContent = "";
            Email email;

            try
            {
                Object content = msg.getContent();

                // Most messages are sent as Multipart objects
                if (content instanceof Multipart)
                {
                    BodyPart part = ((Multipart) content).getBodyPart(0);
                    if (part.isMimeType("text/plain"))
                    {
                        messageContent = (String) part.getContent();
                    }
                } else if (content != null)
                {
                    messageContent = content.toString();
                }

            } catch (Exception ex)
            {
                messageContent = "[Error downloading content]";
                ex.printStackTrace();
            }

            email = new Email(i, from, subject, messageContent, sentDate, true);

            list.add(email);
        }
        System.out.println("Mapping complete");

        return list;
    }

    private static String parseAddresses(Address[] address)
    {
        String listAddress = "";

        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                listAddress += address[i].toString() + ", ";
            }
        }
        if (listAddress.length() > 1) {
            listAddress = listAddress.substring(0, listAddress.length() - 2);
        }

        return listAddress;
    }

}
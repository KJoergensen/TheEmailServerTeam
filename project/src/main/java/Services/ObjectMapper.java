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
        ArrayList<Email> list = new ArrayList<Email>();

        for (int i = 0; i < messages.length; i++)
        {
            Message msg = messages[i];
            Address[] fromAddress = msg.getFrom();
            String from = fromAddress[0].toString();
            String subject = msg.getSubject();
            String toList = parseAddresses(msg.getRecipients(Message.RecipientType.TO));
            String ccList = parseAddresses(msg.getRecipients(Message.RecipientType.CC));
            Date sentDate = msg.getSentDate();
            String messageContent = "";
            Email email;

            try
            {
                Object content = msg.getContent();

                // Most messages are sent as Multipart objects
                if (content instanceof Multipart)
                {
                    // Iterating through bodyparts in Multipart object
                    int count = ((Multipart) content).getCount();
                    for (int x = 0; x < count; x++)
                    {
                        BodyPart part = ((Multipart) content).getBodyPart(x);
                        if (part.isMimeType("text/plain"))
                        {
                            messageContent = (String) part.getContent();
                        }
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
            // print out details of each message
//            System.out.println("Message #" + (i + 1) + ":");
//            System.out.println("\t From: " + from);
//            System.out.println("\t To: " + toList);
//            System.out.println("\t CC: " + ccList);
//            System.out.println("\t Subject: " + subject);
//            System.out.println("\t Message: " + messageContent);
            email = new Email(i, toList, from, subject, messageContent, sentDate, true);
//            System.out.println("email " + email);

            list.add(email);
        }

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
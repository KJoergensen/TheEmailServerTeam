package Utilities;

import Models.Email;
import Views.InboxView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message.RecipientType;


public class EmailReceiver
{
    String protocol = "imap";
    String host = "imap.gmail.com";
    String port = "993";
    private Email email;
    private InboxView inboxView;
    private ArrayList<Email> list;

    //public EmailReceiver() {
    //    inboxView = new InboxView(email);
    //}

//    public ArrayList<Email> getEmails ()
//    {
//        list = new ArrayList<Email>();
//
//        // TODO: Some method that contacts gmail server
//
//        return list;
//    }

    public ArrayList<Email> downloadEmails(String userName, String password) throws Exception {
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);

        try {
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);

            // fetches new messages from server
            Message[] messages = folderInbox.getMessages();
            ArrayList<Email> list = new ArrayList<>();

            for (int i = 0; i < messages.length; i++) {
                Message msg = messages[i];
                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String toList = parseAddresses(msg.getRecipients(Message.RecipientType.TO));
                String ccList = parseAddresses(msg.getRecipients(RecipientType.CC));
                Date sentDate = msg.getSentDate();
                String messageContent = "";

                try {
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
                                messageContent = (String)part.getContent();
                            }
                        }
                    }
                    else if (content != null)
                    {
                        messageContent = content.toString();
                    }

                } catch (Exception ex) {
                    messageContent = "[Error downloading content]";
                    ex.printStackTrace();
                }

                // print out details of each message
                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t To: " + toList);
                System.out.println("\t CC: " + ccList);
                System.out.println("\t Subject: " + subject);
                //System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Message: " + messageContent);
                email = new Email(i, toList, from, subject, messageContent, sentDate, true);
                System.out.println("email " + email);
                //list.add(email);
                //inboxView.showInboxMessage(email);
                list.add(email);
            }

            // disconnect
            folderInbox.close(false);
            store.close();

            return list;

        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Properties getServerProperties(String protocol, String host, String port) {
        Properties properties = new Properties();

        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);

        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));

        return properties;
    }

    private String parseAddresses(Address[] address) {
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

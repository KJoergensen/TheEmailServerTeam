package Utilities;

import Models.Email;
import Services.ObjectMapper;
import java.util.*;
import javax.mail.*;

public class EmailReceiver
{
    private String protocol = "imap";
    private String host = "imap.gmail.com";
    private String port = "993";
    private Message[] messages;
    private Message[] newMessages;

    private String user;
    private String pass;

    public ArrayList<Email> downloadEmails(String userName, String password)
    {
        this.user = userName;
        this.pass = password;

        System.out.println("Receiving emails...");

        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);

        try {
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(user, pass);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);

            // fetches new messages from server
            messages = folderInbox.getMessages();
            // mapping the messages to a list of email objects
            ArrayList<Email> emailList = ObjectMapper.mapObjects(messages);

            // disconnect
            folderInbox.close(false);
            store.close();

            return emailList;

        } catch (NoSuchProviderException ex) {
//            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
//            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Could not map the messages");
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public ArrayList<Email> updateInbox()
    {
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);
        ArrayList<Email> emailList = new ArrayList<>();

        try {
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(user, pass);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);

            // fetches new messages from server
            newMessages = folderInbox.getMessages();

            if (newMessages.length > messages.length)
            {
                System.out.println("New emails available");
                newMessages = removeDuplicates(messages, newMessages);

                // mapping the messages to a list of email objects
                emailList = ObjectMapper.mapObjects(newMessages);

                // Adding new mails to messages array
                addNewMailToMessages(newMessages);
            }
            else
            {
                System.out.println("No new emails");
            }

            // disconnect
            folderInbox.close(false);
            store.close();

            return emailList;

        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Could not map the messages");
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private void addNewMailToMessages(Message[] newMessages)
    {
        Message[] newArray = new Message[messages.length + newMessages.length];

        for (int i = 0; i < messages.length; i++)
        {
            newArray[i] = messages[i];
        }
        for (int i = 0; i < newMessages.length; i++)
        {
            newArray[messages.length+i] = newMessages[i];
        }

        messages = newArray;
    }

    private Message[] removeDuplicates(Message[] array1, Message[] array2)
    {
        int count = array2.length - array1.length;
        Message[] newArray = new Message[count];

        for (int i = 0; i < count; i++)
        {
            newArray[i] = array2[array1.length + i];
        }

        return newArray;
    }

    private Properties getServerProperties(String protocol, String host, String port)
    {
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
}

package Utilities;

import Models.Email;
import Services.ObjectMapper;
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

    public ArrayList<Email> downloadEmails(String userName, String password) throws Exception
    {
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
            // mapping the messages to a list of email objects
            ArrayList<Email> list = ObjectMapper.mapObjects(messages);

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

    private String parseAddresses(Address[] address)
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

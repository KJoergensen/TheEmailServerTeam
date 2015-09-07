package Utilities;

import Models.Email;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.security.GeneralSecurityException;
import java.util.*;

public class EmailReceiver
{
    public ArrayList<Email> getEmails ()
    {
        ArrayList<Email> list = new ArrayList<Email>();

        // TODO: Some method that contacts gmail server

        return list;
    }

    public void downloadEmails(String protocol, String host, String port,
                               String userName, String password, long tennantId) throws Exception {
        try {

            Properties properties = getPropertiesForProtocol(protocol, host,
                    port);
            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName,
                                    password);// Specify the Username and the
                            // PassWord
                        }
                    });
            Store store = session.getStore(protocol);
            store.connect(userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);
            System.out.println("HOST: " + store.getURLName().getHost());
            // fetches new messages from server

            Message[] messages = folderInbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
            for (int i = 0; i < messages.length; i++) {
                Message msg = messages[i];
                String attachments = "";
//				Collection<byte[]> attachments = new ArrayList<byte[]>(getAttachmentFiles(msg));
                System.out.println(String.format("Is recent: %s, Is seen: %s",
                        msg.isSet(Flags.Flag.RECENT),
                        msg.isSet(Flags.Flag.SEEN)));
                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String toList = parseAddresses(msg
                        .getRecipients(Message.RecipientType.TO));
                // Creates arraylists to contain Collection elements for TO.
                Collection<String> JMATolist = new ArrayList<String>();
                JMATolist.add(toList);
                String ccList = parseAddresses(msg
                        .getRecipients(Message.RecipientType.CC));
                // Creates arraylists to contain Collection elements for CC.
                Collection<String> JMACcList = new ArrayList<String>();
                JMACcList.add(ccList);
                // Creates arraylists to contain Collection elements for
                // BCC.
                String bccList = parseAddresses(msg
                        .getRecipients(Message.RecipientType.BCC));
                Collection<String> JMABccList = new ArrayList<String>();
                JMABccList.add(bccList);
                Date sentDate = msg.getSentDate();
                Address[] replyTo = msg.getReplyTo();
                // Casts replyTo to a String.
                String JMAReplyTo = replyTo.toString();
                int messageId = i + 1;

                String contentType = msg.getContentType();
                String messageContent = "";

                // Creates email object with all received email's.
                RFC822Message Message = RFC822Message.newBuilder()
                        .messageId(i + 1 + "")
                        .date(sentDate)
                        .tenantId(tennantId)
                        .from(from)
                        .to(JMATolist)
                        .replyTo(JMAReplyTo)
                        .cc(JMABccList)
                        .bcc(JMABccList)
                        .subject(subject)
                        .body(messageContent)
                                //.attachments(attachments)
                        .build();
//				RFC822Message Message = new RFC822Message(i + 1 + "", tennantId,
//						sentDate, from, subject, JMAReplyTo, JMATolist,
//						JMACcList, JMABccList, null, null, messageContent,
//						null, attachments);
                //Message.setAttachment();
                m_messageQueue.write(Message);
                msg.setFlag(Flags.Flag.SEEN, true);
            }
            folderInbox.close(false);
            store.close();
            // disconnect

        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
    }
    private Properties getPropertiesForProtocol(String protocol, String host,
                                                String port) throws GeneralSecurityException {
        Properties properties;
        if (protocol.equalsIgnoreCase("imaps")
                || protocol.equalsIgnoreCase("imap")) {
            properties = getServerProperties(protocol, host, port);
            properties.put("mail.imaps.ssl.enable", true);
            properties.put("mail.imaps.ssl.trust", "*");
            MailSSLSocketFactory factory = new MailSSLSocketFactory();
            factory.setTrustAllHosts(true);
            properties.put("mail.imap.ssl.socketFactory", factory);
        } else if (protocol.equalsIgnoreCase("pop3")) {
            properties = getServerProperties(protocol, host, port);
            properties.put("mail.pop3.ssl.enable", true);
            properties.put("mail.pop3.ssl.trust", "*");
            MailSSLSocketFactory factory = new MailSSLSocketFactory();
            factory.setTrustAllHosts(true);
            properties.put("mail.pop3.ssl.socketFactory", factory);
        } else {
            throw new IllegalArgumentException(
                    "Protocol should be either imap/imaps or pop3");
        }
        return properties;
    }

}

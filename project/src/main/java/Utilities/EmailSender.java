package Utilities;

import Models.Email;

import java.io.UnsupportedEncodingException;

public class EmailSender
{
    public void sendEmail(Email email)
    {
        // TODO: A method that sends the emails to gmail server
    }

    public void sendEmail(Session session, Collection<String> mailTo,
                          Collection<String> mailCC, Collection<String> mailBCC,
                          String comment, String inReplyTo, Date date, long tenantId,
                          String msgId, String subject, String text, String name,
                          String mailFrom, Map<String, String> header, String attachment) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(mailFrom, name));

            if (inReplyTo != null) {
                msg.setReplyTo(InternetAddress.parse(inReplyTo, false));
            }

            Set<String> keys = new HashSet<String>(header.keySet());
            for (String key : keys) {
                String currVal = header.get(key);
                msg.addHeader(key, currVal);
            }

            msg.setSubject(subject, "UTF-8");
            msg.setText(text, "UTF-8");
            msg.setSentDate(date);

            for (String email : mailTo) {
                msg.addRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email, false));
            }

            for (String email : mailCC) {
                msg.addRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(email, false));
            }

            for (String email : mailBCC) {
                msg.addRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(email, false));
            }

            if (attachment != null) {
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(text);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
//				messageBodyPart = new MimeBodyPart();
//				String filename = attachment.getName();
//				DataSource source = new FileDataSource(
//						attachment.getAbsolutePath());
//				messageBodyPart.setDataHandler(new DataHandler(source));
//				messageBodyPart.setFileName(filename);
//				multipart.addBodyPart(messageBodyPart);
//				msg.setContent(multipart);

//				for(String attachPart : attachment){
//                                    MimeBodyPart mbp = new MimeBodyPart();
//				    DataSource ds = new ByteArrayDataSource(attachPart, "application/octet-stream");
//                                    mbp.setDataHandler(new DataHandler(ds));
//                                    multipart.addBodyPart(mbp);
//				}
//				msg.setContent(multipart);
            }

            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("Email Sent Successfully!!");
        } catch (MessagingException e) {
            System.out.println("Could not send mail, saving backup in db to resend in 5 min.");
            //saveEmailToDb(session, mailTo,
            //		mailCC, mailBCC,
            //		comment, inReplyTo, date, tenantId,
            //		msgId, subject, text, name,
            //		mailFrom, header, attachment);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

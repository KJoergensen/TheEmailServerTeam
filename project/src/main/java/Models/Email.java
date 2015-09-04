package Models;

import java.util.Date;

public class Email {
    public String to;
    public String from;
    public String subject;
    public String body;
    public int id;
    public Date date;
    public boolean newEmail; // To check if read/unread
}

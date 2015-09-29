package Models;

import java.util.Date;

public class Email {

    private int id;
    private String to;
    private String from;
    private String subject;
    private String body;
    private Date date;
    private boolean newEmail; // To check if read/unread


    public Email(int id, String to, String from, String subject, String body,  Date date, boolean newEmail) {

        this.id = id;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.newEmail = newEmail;
    }

    // Without to list
    public Email(int id, String from, String subject, String body,  Date date, boolean newEmail)
    {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.newEmail = newEmail;
    }

    public String getTo() { return to; }


    public String getFrom() {
        return from;
    }



    public String getSubject() {
        return subject;
    }


    public String getBody() {
        return body;
    }



    public int getId() {
        return id;
    }



    public Date getDate() {
        return date;
    }


}

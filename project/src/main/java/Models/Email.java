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

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isNewEmail() {
        return newEmail;
    }

    public void setNewEmail(boolean newEmail) {
        this.newEmail = newEmail;
    }
}

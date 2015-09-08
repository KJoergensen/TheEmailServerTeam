package Models;

import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public int id;
    public ArrayList<Email> inbox;

    public User() {}

    public User(String username, String password, int id, ArrayList<Email> inbox) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.inbox = inbox;
    }

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Email> getInbox() {
        return inbox;
    }

    public void setInbox(ArrayList<Email> inbox) {
        this.inbox = inbox;
    }

}

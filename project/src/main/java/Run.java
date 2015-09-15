import Models.Email;
import Models.User;
import Utilities.EmailSender;

public class Run
{
    public static void main(String[] args)
    {
        //LogHandler.addNewRow("System", "Application started");
        //new LoginView();

        User user = new User();
        user.setUsername("keamailtest@gmail.com");
        user.setPassword("mailtester");

        Email email = new Email();
        email.setTo("t.hyldgaard92@gmail.com");
        email.setFrom("keamailtest@gmail.com");
        email.setSubject("Testing...");
        email.setBody("Can you read this...?");

        EmailSender.sendMessage(email, user);


    }
}

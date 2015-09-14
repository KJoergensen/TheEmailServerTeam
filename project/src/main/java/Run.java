import Services.LogHandler;
import Views.LoginView;
import Views.InboxView;
import Views.WriteNewEmailView;

public class Run
{
    public static void main(String[] args)
    {
        LogHandler.addNewRow("System", "Application started");
        new LoginView();


    }
}

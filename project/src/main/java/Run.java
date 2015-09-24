
import Views.LoginView;

import javax.swing.*;

public class Run extends JApplet
{
    public void start()
    {
        new LoginView();
    }

    public void init()
    {

    }

    public void stop()
    {

    }

    public static void main(String[] args)
    {
        //LogHandler.addNewRow("System", "Application started");
        System.out.println("Launching application");

        new LoginView();



    }
}

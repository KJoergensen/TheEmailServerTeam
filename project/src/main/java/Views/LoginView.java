package Views;

import javax.swing.*;
import java.awt.*;


/***
 * Created by Hisayo on 04/09/15.
 */
public class LoginView extends JFrame{

    static final String LOGIN ="Login";
    static final String CANCEL = "Cancel";

    private JButton cancelBtn, loginBtn;
    private JTextField emailTxtF, passwordTxtF;

    public LoginView()
    {
        openWindow();
    }

    public void openWindow()
    {
        setTitle("Login");
        setSize(360, 140);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(createUIComponents());
        setLocation(450, 280);
        setResizable(true);
        setVisible(true);

    }

    private JPanel createUIComponents() {

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel emailLabel = new JLabel("Gmail address");
        this.emailTxtF = new JTextField(20);
        northPanel.add(emailLabel);
        northPanel.add(emailTxtF);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel passwordLabel = new JLabel("Password");
        this.passwordTxtF = new JTextField(20);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordTxtF);

        JPanel southPanel = new JPanel(new FlowLayout());
        this.loginBtn = new JButton(LOGIN);
        this.cancelBtn = new JButton(CANCEL);
        southPanel.add(loginBtn);
        southPanel.add(cancelBtn);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.add(northPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(southPanel);

        return mainPanel;
    }
}

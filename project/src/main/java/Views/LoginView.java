package Views;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.*;

/**
 * Created by Hisayo on 04/09/15.
 */
public class LoginView extends JFrame{

    static final String LOGIN ="Login";
    static final String CANCEL = "Cancel";

    private JPanel mainPanel, southPanel, centerPanel, northPanel;
    private JButton cancelBtn, loginBtn;
    private JLabel emailLabel, passwordLabel;
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

        this.northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.emailLabel = new JLabel("Gmail address");
        this.emailTxtF = new JTextField(20);
        this.northPanel.add(emailLabel);
        this.northPanel.add(emailTxtF);

        this.centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.passwordLabel = new JLabel("Password");
        this.passwordTxtF = new JTextField(20);
        this.centerPanel.add(passwordLabel);
        this.centerPanel.add(passwordTxtF);

        this.southPanel = new JPanel(new FlowLayout());
        this.loginBtn = new JButton(LOGIN);
        this.cancelBtn = new JButton(CANCEL);
        this.southPanel.add(loginBtn);
        this.southPanel.add(cancelBtn);

        this.mainPanel = new JPanel(new GridLayout(3,1));
        mainPanel.add(northPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(southPanel);

        return mainPanel;
    }
}

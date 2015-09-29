package Views;

import Controllers.LoginController;
import Services.Validator;
import Utilities.EmailReceiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/***
 * Created by Hisayo on 04/09/15.
 */
public class LoginView extends JFrame implements ActionListener{

    static final String LOGIN ="Login";
    static final String CANCEL = "Cancel";

    private JButton cancelBtn, loginBtn;
    private JTextField emailTxtF;
    private JPasswordField passwordTxtF;
    private LoginController controller;


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
        this.passwordTxtF = new JPasswordField(20);
        this.passwordTxtF.addActionListener(this);

        centerPanel.add(passwordLabel);
        centerPanel.add(passwordTxtF);


        JPanel southPanel = new JPanel(new FlowLayout());
        this.loginBtn = new JButton(LOGIN);
        this.loginBtn.addActionListener(this);
        this.cancelBtn = new JButton(CANCEL);
        this.cancelBtn.addActionListener(this);
        southPanel.add(loginBtn);
        southPanel.add(cancelBtn);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.add(northPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(southPanel);


        return mainPanel;

    }


    public void actionPerformed(ActionEvent e) {
        //click cancel button
        if(e.getSource().equals(this.cancelBtn))
        {
            dispose();
        }

        //define action to click loginBtn
        //define to get email from textField
        if(e.getSource() == this.loginBtn || e.getSource() == this.passwordTxtF)
        {
            System.out.println("button login");
            controller = new LoginController();

            if(controller.validateLogin(this.emailTxtF.getText().trim(), this.passwordTxtF.getPassword()).equals("success"))
            {
                dispose();
                controller.launchInbox();
            }
            else if(controller.validateLogin(this.emailTxtF.getText().trim(), this.passwordTxtF.getPassword()).equals("authenticate failed"))
            {
                JOptionPane.showMessageDialog(this, "Please check your email and password!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
                else if(controller.validateLogin(this.emailTxtF.getText().trim(), this.passwordTxtF.getPassword()).equals("unexpected fail"))
            {
                JOptionPane.showMessageDialog(this, "Please check your internet connection!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please check your email and password!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            setEnabled(false);


        }



    }
}

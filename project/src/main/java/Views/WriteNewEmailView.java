package Views;

import Models.Email;
import Models.User;
import Utilities.EmailSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/***
 * Created by Hisayo on 07/09/15.
 */
public class WriteNewEmailView extends JFrame implements ActionListener{

    private JTextField txtTo,txtSub;
    private JTextArea bodyArea;
    private JButton btnSend, btnCancel;
    private User user;

    public WriteNewEmailView(User user)
    {
        this.user = user;
        openWindow();
    }

    public void openWindow()
    {
        setTitle("Write new email");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(680, 400);//size of window
        add(createUIComponents());
        setResizable(false);
        setVisible(true);
    }

    private JPanel createUIComponents()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel(new GridLayout(2,1));

        JPanel toPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel toLabel = new JLabel("To:");
        this.txtTo = new JTextField(50);
        toPanel.add(toLabel);
        toPanel.add(txtTo);

        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel subLabel = new JLabel("Subject:");
        this.txtSub = new JTextField(50);
        subPanel.add(subLabel);
        subPanel.add(txtSub);

        northPanel.add(toPanel);
        northPanel.add(subPanel);

        JPanel centerPanel = new JPanel();
        this.bodyArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(bodyArea);
        scrollPane.setPreferredSize(new Dimension(650,250));

        centerPanel.add(scrollPane);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnCancel = new JButton("Cancel");
        this.btnCancel.addActionListener(this);
        this.btnSend  = new JButton(" Send ");
        this.btnSend.addActionListener(this);

        southPanel.add(btnCancel);
        southPanel.add(btnSend);

        mainPanel.add(northPanel,BorderLayout.NORTH);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(southPanel,BorderLayout.SOUTH);

        return mainPanel;
    }


    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(btnCancel))
        {
            dispose();
        }

        if(e.getSource().equals(btnSend))
        {
            int id = 0;
            String to = this.txtTo.getText();
            String from = null;
            String sub = this.txtSub.getText();
            String body = this.bodyArea.getText();
            Date date = null;
            boolean newEmail = false;

            //public Email(int id, String to, String from, String subject, String body,  Date date, boolean newEmail)
            Email writeEmail = new Email(id,to,from,sub,body,date,newEmail );
            EmailSender.sendMessage(writeEmail, user);
            dispose();
        }
    }
}

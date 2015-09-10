package Views;

import Models.Email;
import Utilities.EmailReceiver;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;

/***
 * Created by Hisayo on 04/09/15.
 */
public class InboxView extends JFrame implements ActionListener{

    private JButton updateBtn, writeBtn;
    private String []headerTitle = {"Date","Email Address","Subject"};
    //private String []emailData = {"mail@sample.com","subjectExample"};
    private JTable inboxTable;
    private DefaultTableModel tableModel;
    private TextArea messageTxtArea;

    public InboxView(String userName, String password)
    {
        EmailReceiver emailReceiver = new EmailReceiver();
        try {
            emailReceiver.downloadEmails(userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        openWindow();
        clickEmailAndSubjectListener();
    }

    public void openWindow()
    {
        setTitle("Inbox");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);//size of window
        add(createUIComponents());
        setResizable(true);
        setVisible(true);
    }

    private JSplitPane createUIComponents()
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        this.updateBtn = new JButton("Update");
        this.updateBtn.addActionListener(this);
        this.writeBtn = new JButton(" Write ");
        this.writeBtn.addActionListener(this);
        leftPanel.add(updateBtn);
        leftPanel.add(writeBtn);

        JPanel rightPanel = new JPanel();//right side of window
        rightPanel.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();//inbox table

        //to add new email, use DefaultTableModel.
        this.tableModel = new DefaultTableModel(this.headerTitle,1);//set the header at 1st row, 1 column

        //model set in table
        this.inboxTable = new JTable(tableModel);

        //make a hedder size to the table
        int[] width = new int[this.headerTitle.length];
        width[0]=50;
        width[1]=100;
        width[2]=700;
        for(int i = 0; i<width.length;i++)
        {
            TableColumn column = inboxTable.getColumnModel().getColumn(i);
            column.setMinWidth(width[i]);
            column.setMaxWidth(width[i]);
        }

        //need to define arrayList<Email> and tableModel.setValueAt();


        //you can scroll the table
        JScrollPane scrollPane = new JScrollPane(inboxTable);
        scrollPane.setPreferredSize(new Dimension(850,200));//it effects to make size of table
        northPanel.add(scrollPane);

        JPanel southPanel = new JPanel();//message textArea
        this.messageTxtArea = new TextArea();
        messageTxtArea.setPreferredSize(new Dimension(850,200));
        southPanel.add(messageTxtArea);


        rightPanel.add(northPanel,BorderLayout.NORTH);
        rightPanel.add(southPanel,BorderLayout.SOUTH);

        //to divide window right and left
        JSplitPane splitPane = new JSplitPane();
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        return splitPane;
    }

    public void showInboxMessage(ArrayList<Email> emails)
    {
        int row = 0;
        for(Email email : emails)
        {
            String getDate = email.getDate().toString();
            String emailFrom = email.getFrom();
            String subject = email.getSubject();
            System.out.println(getDate + " " + emailFrom + " " + subject);
            this.tableModel.setValueAt(getDate,row,0);
            this.tableModel.setValueAt(emailFrom,row,1);
            this.tableModel.setValueAt(subject,row, 2);
        }


    }

    public void actionPerformed(ActionEvent e) {
        //to define the inbox button
        if(e.getSource().equals(updateBtn))
        {
            JOptionPane.showMessageDialog(this, "it it under construction!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        if(e.getSource().equals(writeBtn))
        {
            new WriteNewEmailView();
        }
    }

    public void clickEmailAndSubjectListener()
    {
        //MouseListener
        this.inboxTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = inboxTable.getSelectedRow();
                int column = inboxTable.getSelectedColumn();


                messageTxtArea.setText(row + "::" + "column" + column);
                //have to define what happen after click the email and subject
                System.out.println("Row" + row + "::" + "column" + column);
//                if(row == messageId)
//                    showmessage;





            }
        });
    }
}

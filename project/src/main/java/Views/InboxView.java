package Views;

import Controllers.InboxController;
import Models.Email;
import Models.User;
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
public class InboxView extends JFrame implements ActionListener, MouseListener{

    private JButton updateBtn, writeBtn;
    private String []headerTitle = {"Date","Email Address","Subject"};
    private JTable inboxTable;
    private DefaultTableModel tableModel;
    private TextArea messageTxtArea;
    private ArrayList<Email> emails;
    private InboxController inboxController;

    public InboxView(InboxController inboxController, ArrayList<Email> emails)
    {
        this.inboxController = inboxController;
        this.emails = emails;
        openWindow();
        showInboxMessage(emails);
    }

    public void openWindow()
    {
        setTitle("Inbox");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 740);//size of window
        add(createUIComponents());
        setResizable(false);
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
        this.inboxTable.addMouseListener(this);

        //make a hedder size to the table
        int[] width = new int[this.headerTitle.length];
        width[0]=130;
        width[1]=270;
        width[2]=680;
        for(int i = 0; i<width.length;i++)
        {
            TableColumn column = inboxTable.getColumnModel().getColumn(i);
            column.setMinWidth(width[i]);
            column.setMaxWidth(width[i]);
        }

        //you can scroll the table
        JScrollPane scrollPane = new JScrollPane(inboxTable);
        scrollPane.setPreferredSize(new Dimension(1080,320));//it effects to make size of table
        northPanel.add(scrollPane);

        JPanel southPanel = new JPanel();//message textArea
        this.messageTxtArea = new TextArea();
        messageTxtArea.setPreferredSize(new Dimension(1080,320));
        messageTxtArea.setEditable(false);
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
        this.tableModel.setNumRows(emails.size());
        int row = 0;

        for (Email e : emails)
        {
            String getDate = e.getDate().toString();
            String emailFrom = e.getFrom();
            String subject = e.getSubject();

            System.out.println(getDate + " " + emailFrom + " " + subject);

            this.tableModel.setValueAt(getDate, row, 0);
            this.tableModel.setValueAt(emailFrom,row,1);
            this.tableModel.setValueAt(subject,row, 2);

            row++;
        }
        //should delete all old emails from the list

    }

    public void actionPerformed(ActionEvent e) {
        //to define the inbox button
        if(e.getSource().equals(updateBtn))
        {
            //JOptionPane.showMessageDialog(this, "it it under construction!", "Info", JOptionPane.INFORMATION_MESSAGE);
            this.emails = this.inboxController.updateInbox();
            showInboxMessage(this.emails);
        }

        if(e.getSource().equals(writeBtn))
        {
            new WriteNewEmailView();
        }


    }

// Mouse Actionlistener
    public void mouseClicked(MouseEvent e) {

        int row = inboxTable.getSelectedRow();
        //int column = inboxTable.getSelectedColumn();

        for(Email email : emails)
        {
            //int value = tableModel.getValueAt(row,0);
            if (email.getId() == row)
            {
                System.out.println(email.getBody());
                messageTxtArea.setText(email.getBody());

            }



        }
        //have to define what happen after click the email and subject
//        System.out.println("Row" + row + "::" + "column" + column);



    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}

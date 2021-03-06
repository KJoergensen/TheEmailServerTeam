package Views;

import Controllers.InboxController;
import Models.Email;
import Models.User;
import java.util.ArrayList;
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
    private User user;

    public InboxView(InboxController inboxController, ArrayList<Email> emails, User user)
    {
        this.inboxController = inboxController;
        this.emails = emails;
        this.user = user;
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

        //make a header size to the table
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
        this.emails = emails;
        this.tableModel.setNumRows(emails.size());
        int row = 0;

        for (Email e : emails)
        {
            String getDate = e.getDate().toString();
            String emailFrom = e.getFrom();
            String subject = e.getSubject();

            this.tableModel.fireTableDataChanged();
            this.tableModel.setValueAt(getDate, row, 0);
            this.tableModel.setValueAt(emailFrom,row,1);
            this.tableModel.setValueAt(subject,row, 2);

            row++;
        }
    }

    // Update button
    public void actionPerformed(ActionEvent e) {
        //to define the inbox button
        if(e.getSource().equals(updateBtn))
        {
            showInboxMessage(this.inboxController.updateInbox());
        }

        if(e.getSource().equals(writeBtn))
        {
            try
            {
                new WriteNewEmailView(user, inboxController);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    // Mouse Actionlistener
    public void mouseClicked(MouseEvent e) {

        int row = inboxTable.getSelectedRow();

        for(Email email : emails)
        {
            if (email.getId() == row)
            {
                messageTxtArea.setText(email.getBody());
            }
        }
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

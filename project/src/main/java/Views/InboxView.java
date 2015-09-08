package Views;

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
    private TextArea messageTxtArea;

    public InboxView()
    {
        openWindow();
        clickEmailAndSubject();
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
        DefaultTableModel tableModel = new DefaultTableModel(this.headerTitle,15);//set the header at 1st row, 1 column

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

    public void clickEmailAndSubject()
    {
        //MouseListener
        this.inboxTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = inboxTable.getSelectedRow();
                int column = inboxTable.getSelectedColumn();

                //have to define what happen after click the email and subject
                System.out.println("Row" + row + "::" + "column" + column);

                messageTxtArea.setText("Row" + row + "::" + "column" + column);




            }
        });
    }
}

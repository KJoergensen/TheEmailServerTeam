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

    private JButton inboxBtn;
    private String []headerTitle = {"Email Address","Subject"};
    //private String []emailData = {"mail@sample.com","subjectExample"};
    private JTable inboxTable;

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
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.inboxBtn = new JButton("Inbox");
        this.inboxBtn.addActionListener(this);
        leftPanel.add(inboxBtn);

        JPanel rightPanel = new JPanel();

        //to add new email, use DefaultTableModel.
        DefaultTableModel tableModel = new DefaultTableModel(this.headerTitle,5);//set the header at 1st row, 1 column

        //model set in table
        this.inboxTable = new JTable(tableModel);

        //make a hedder size to the table
        int[] width = new int[this.headerTitle.length];
        width[0]=150;
        width[1]=700;
        for(int i = 0; i<width.length;i++)
        {
            TableColumn column = inboxTable.getColumnModel().getColumn(i);
            column.setMinWidth(width[i]);
            column.setMaxWidth(width[i]);
        }

        //need to define arrayList<Email> and tableModel.setValueAt();


        //you can scroll the table
        JScrollPane scrollPane = new JScrollPane(inboxTable);
        scrollPane.setPreferredSize(new Dimension(850,450));//it effects to make size of table
        rightPanel.add(scrollPane);

        //to divide window right and left
        JSplitPane splitPane = new JSplitPane();
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        return splitPane;
    }

    public void actionPerformed(ActionEvent e) {
        //to define the inbox button
        if(e.getSource().equals(inboxBtn))
        {
            JOptionPane.showMessageDialog(this, "it it under construction!", "Info", JOptionPane.INFORMATION_MESSAGE);
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


            }
        });
    }
}

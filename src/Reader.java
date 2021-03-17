import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

/*
 * Created by JFormDesigner on Fri Mar 27 18:14:23 IST 2020
 */



/**
 * @author Vignesh
 */
public class Reader extends JFrame {
    PDDocument document = null;
    PDFTextStripper pdfStripper;
    int page = 1;
    Login User;
    StarRater starRater;

    public Reader(Login User) {
        initComponents();
        this.User = User ;
        //JOptionPane.showMessageDialog(null,"Inside reader con "+User.isbn);
        ImageIcon  icon = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\vg.jpg");
        label3.setIcon(icon);
        getContentPane().setBackground(new Color(32, 32, 32));
        menuBar1.setBackground(Color.BLACK);
         if (User.isbn != 0) {
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                    String query = "select rating from ratingTable where username='"+User.username
                            +"' and book_isbn='"+User.isbn+"'";
                    Statement Stmt = con.createStatement();
                    ResultSet rs = Stmt.executeQuery(query);
                    if(rs.next()){
                        starRater.setRating(rs.getInt(1));
                    }
                    con.close();
                } catch (Exception ee) {
                    System.out.println(ee);
                }
         }
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        // TODO add your code here
        //JOptionPane.showMessageDialog(null,"inside menu "+User.isbn);
        if(User.isbn!=0) {
            try {
               // JOptionPane.showMessageDialog(null,"inside if"+User.isbn);
                User.se = new Timestamp(new Date().getTime());
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String insert_query = "insert into log"
                        + " (username, book_isbn, session_begin,session_end)"
                        + " values (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insert_query);
                ps.setString(1,User.username);
                ps.setInt(2,User.isbn);
                ps.setTimestamp(3,User.sb);
                ps.setTimestamp(4,User.se);
                ps.executeUpdate();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
            //JOptionPane.showMessageDialog(null, User.sb + " " + User.se);
        }
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // invoke the showsOpenDialog function to show the save dialog
        int r = j.showOpenDialog(this);
        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            try {
                // set the label to the path of the selected file
                if (document != null){
                    document.close();
                    page = 1;
                }
                //Loading an existing document
                String path = j.getSelectedFile().getAbsolutePath();
                File file = new File(path);
                document = PDDocument.load(file);
                label1.setText("/"+document.getNumberOfPages());
                //metaData
                PDDocumentInformation info = document.getDocumentInformation();
                /*System.out.println( "Title=" + info.getTitle() );
                System.out.println( "Author=" + info.getAuthor() );
                System.out.println( "Subject=" + info.getSubject() );
                System.out.println( "Keywords=" + info.getKeywords() );
                System.out.println( "Creator=" + info.getCreator() );
                System.out.println( "Producer=" + info.getProducer() );
                System.out.println( "Creation Date=" + info.getCreationDate() );
                System.out.println( "Modification Date=" + info.getModificationDate());
                System.out.println( "Trapped=" + info.getTrapped() );*/
                //add metadata to table
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                Object[] row = { path,info.getTitle(), info.getAuthor() };
                model.addRow(row);
                //Instantiate PDFTextStripper class
                pdfStripper = new PDFTextStripper();
                //Retrieving text from PDF document
                pdfStripper.setStartPage(page);
                pdfStripper.setEndPage(page);
                String text = pdfStripper.getText(document);
                textArea1.setText(text);
                textArea1.setSelectionStart(0);
                textArea1.setSelectionEnd(0);
                textField1.setText(String.valueOf(page));
                //Closing the document
                //document.close();
                User.isbn=0;
            }
            catch(IOException io){
                JOptionPane.showMessageDialog(null,"Open a PDF Document");
            }
            textArea1.requestFocus();
        }
        // if the user cancelled the operation
        else
            textArea1.setText("the user cancelled the operation");
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        if(document!=null) {
            try {
                page = 1;
                textField1.setText(String.valueOf(page));
                pdfStripper.setStartPage(page);
                pdfStripper.setEndPage(page);
                String text = pdfStripper.getText(document);
                textArea1.setText(text);
                textArea1.setSelectionStart(0);
                textArea1.setSelectionEnd(0);
            } catch (IOException io) {
                System.out.print(io);
            }
            textArea1.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Open book First");
        }
    }/**
 * @author Vignesh
 */

    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code here
        if(document!=null) {
            try {
                page = document.getNumberOfPages();
                textField1.setText(String.valueOf(page));
                pdfStripper.setStartPage(page);
                pdfStripper.setEndPage(page);
                String text = pdfStripper.getText(document);
                textArea1.setText(text);
                textArea1.setSelectionStart(0);
                textArea1.setSelectionEnd(0);
            } catch (IOException io) {
                System.out.print(io);
            }
            textArea1.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Open book First");
        }
    }

    private void button3MouseClicked(MouseEvent e) {
        // TODO add your code here
        if(document!=null) {
            try {
                if (page <= 1) {
                    JOptionPane.showMessageDialog(null, "Page limit exceeded");
                } else {
                    page--;
                    textField1.setText(String.valueOf(page));
                    pdfStripper.setStartPage(page);
                    pdfStripper.setEndPage(page);
                    String text = pdfStripper.getText(document);
                    textArea1.setText(text);
                    textArea1.setSelectionStart(0);
                    textArea1.setSelectionEnd(0);
                }
            } catch (IOException io) {
                System.out.print(io);
            }
            textArea1.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Open book First");
        }
    }

    private void button4MouseClicked(MouseEvent e) {
        // TODO add your code here
        if (document!=null) {
            try {
                if (page >= document.getNumberOfPages()) {
                    JOptionPane.showMessageDialog(null, "Page limit exceeded");
                } else {
                    page++;
                    textField1.setText(String.valueOf(page));
                    pdfStripper.setStartPage(page);
                    pdfStripper.setEndPage(page);
                    String text = pdfStripper.getText(document);
                    //String[] docxLines = text.split(System.lineSeparator());
                    //for (String line : docxLines) {
                        //textArea1.append(line);
                      //  textArea1.append("\n");
                    //}
                    textArea1.setText(text);
                    textArea1.setSelectionStart(0);
                    textArea1.setSelectionEnd(0);
                }
            } catch (IOException io) {
                System.out.print(io);
            }
            textArea1.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Open book First");
        }
    }

    private void button5MouseClicked(MouseEvent e) {
        // TODO add your code here
        if (document!=null) {
            try {
                page = Integer.valueOf(textField1.getText());
                if (page < 1 || page >= document.getNumberOfPages()) {
                    JOptionPane.showMessageDialog(null, "Page limit exceeded");
                } else {
                    pdfStripper.setStartPage(page);
                    pdfStripper.setEndPage(page);
                    String text = pdfStripper.getText(document);
                    textArea1.setText(text);
                    textArea1.setSelectionStart(0);
                    textArea1.setSelectionEnd(0);
                }
            } catch (IOException io) {
                System.out.print(io);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "There is no page numbered '" + textField1.getText() + "' in this book");
            }
            textArea1.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Open book First");
        }
    }

    private void button6MouseClicked(MouseEvent e) {
        // TODO add your code here
        boolean found = false;
        String search_value = textField2.getText();
        String cell_value;
        int row = table1.getRowCount();
        for (int i = 0; i < row; i++) {
            cell_value = table1.getModel().getValueAt(i, 0).toString();
            if (search_value.equals(cell_value)){
                JOptionPane.showMessageDialog(null,cell_value);
                table1.addRowSelectionInterval(i,i);
                found = true;
            }
        }
        if(found == false){
            JOptionPane.showMessageDialog(null,"Reader has finished searching your library. No matches were found.");
        }
        textArea1.requestFocus();
    }

    private void table1MouseClicked(MouseEvent e) {
        // TODO add your code here
        DefaultTableModel model = (DefaultTableModel)table1.getModel();
        int selectedRowIndex = table1.getSelectedRow();
        String path = model.getValueAt(selectedRowIndex, 0).toString();
        OpenDocument(path);
    }

    public void OpenDocument(String path){

        try {
            if (document != null) {
                document.close();
                page = 1;
            }
            File file = new File(path);
            document = PDDocument.load(file);
            label1.setText("/" + document.getNumberOfPages());
            pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(page);
            pdfStripper.setEndPage(page);
            String text = pdfStripper.getText(document);
            textArea1.setText(text);
            textArea1.setSelectionStart(0);
            textArea1.setSelectionEnd(0);
            textField1.setText(String.valueOf(page));
        }
        catch (Exception ee){

        }

    }

    private void menuItem5ActionPerformed(ActionEvent e) {
        // TODO add your code here
        try{
            document.close();
        }
        catch (Exception io){ }
        JOptionPane.showMessageDialog(null,"Thank you "+ User.username);
        try { User.se = new Timestamp(new Date().getTime()); }
        catch (Exception ee){System.out.print(ee);}
        if(User.isbn!=0) {
            try {
                User.se = new Timestamp(new Date().getTime());
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String insert_query = "insert into log"
                        + " (username, book_isbn, session_begin,session_end)"
                        + " values (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insert_query);
                ps.setString(1,User.username);
                ps.setInt(2,User.isbn);
                ps.setTimestamp(3,User.sb);
                ps.setTimestamp(4,User.se);
                ps.executeUpdate();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
            //JOptionPane.showMessageDialog(null, User.sb + " " + User.se);
        }
        this.dispose();
        new Login().setVisible(true);
    }

    private void textField2KeyPressed(KeyEvent e) {
        // TODO add your code here
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            button6MouseClicked(null);
        }
    }

    private void textArea1KeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //System.out.println(key);
        if (key == 37) {
            button3MouseClicked(null);
        }
        else if (key == 39) {
            button4MouseClicked(null);
        }
        else if (key == 107) {
            button7MouseClicked(null);
        }
        else if (key == 109) {
            button8MouseClicked(null);
        }

    }

    private void textField1KeyPressed(KeyEvent e) {
        // TODO add your code here
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            button5MouseClicked(null);
        }
    }

    private void button7MouseClicked(MouseEvent e) {
        // TODO add your code here
        Font f1 = textArea1.getFont();
        Font f2 = new Font(f1.getFontName(), f1.getStyle(), f1.getSize()+1);
        textArea1.setFont(f2);
    }

    private void button8MouseClicked(MouseEvent e) {
        // TODO add your code here
        Font f1 = textArea1.getFont();
        Font f2 = new Font(f1.getFontName(), f1.getStyle(), f1.getSize()-1);
        textArea1.setFont(f2);
    }

    private void menuItem3ActionPerformed(ActionEvent e) {
        // TODO add your code here
        new Settings(User,this).setVisible(true);
    }

    private void button9MouseClicked(MouseEvent e) {
        // TODO add your code here
        try { User.se = new Timestamp(new Date().getTime()); }
        catch (Exception ee){System.out.print(ee);}
        if(User.isbn!=0) {
            try {
                User.se = new Timestamp(new Date().getTime());
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String insert_query = "insert into log"
                        + " (username, book_isbn, session_begin,session_end)"
                        + " values (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insert_query);
                ps.setString(1,User.username);
                ps.setInt(2,User.isbn);
                ps.setTimestamp(3,User.sb);
                ps.setTimestamp(4,User.se);
                ps.executeUpdate();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
            //JOptionPane.showMessageDialog(null, User.sb + " " + User.se);
        }
        new ELibrary(User).setVisible(true);
        this.dispose();
    }

    private void thisWindowClosing(WindowEvent e) {
        // TODO add your code here
        try { User.se = new Timestamp(new Date().getTime()); }
        catch (Exception ee){System.out.print(ee);}
        //JOptionPane.showMessageDialog(null,User.sb+"\n"+User.se);
        if(User.isbn!=0) {
            try {
                User.se = new Timestamp(new Date().getTime());
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String insert_query = "insert into log"
                        + " (username, book_isbn, session_begin,session_end)"
                        + " values (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insert_query);
                ps.setString(1,User.username);
                ps.setInt(2,User.isbn);
                ps.setTimestamp(3,User.sb);
                ps.setTimestamp(4,User.se);
                ps.executeUpdate();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
            //JOptionPane.showMessageDialog(null, User.sb + " " + User.se);
        }
        this.dispose();
    }

    class JTable extends javax.swing.JTable {
        public boolean isCellEditable(int row,int column){
            return false;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem5 = new JMenuItem();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        textField1 = new JTextField();
        button5 = new JButton();
        textField2 = new JTextField();
        button6 = new JButton();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();
        label1 = new JLabel();
        label2 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button7 = new JButton();
        button8 = new JButton();
        label3 = new JLabel();
        button9 = new JButton();

        //======== this ========
        setTitle("E-Book Reader");
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\ebook.png").getImage());
        setBackground(new Color(32, 32, 32));
        setAutoRequestFocus(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("File");
                menu1.setBackground(new Color(32, 32, 32));
                menu1.setForeground(new Color(204, 204, 255));

                //---- menuItem2 ----
                menuItem2.setText("Open");
                menuItem2.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\folder.png"));
                menuItem2.setBackground(new Color(32, 32, 32));
                menuItem2.setForeground(new Color(204, 204, 255));
                menuItem2.addActionListener(e -> menuItem2ActionPerformed(e));
                menu1.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText("Settings");
                menuItem3.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\settings.png"));
                menuItem3.setBackground(new Color(32, 32, 32));
                menuItem3.setForeground(new Color(204, 204, 255));
                menuItem3.addActionListener(e -> menuItem3ActionPerformed(e));
                menu1.add(menuItem3);

                //---- menuItem5 ----
                menuItem5.setText("Logout");
                menuItem5.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\logout.png"));
                menuItem5.setBackground(new Color(32, 32, 32));
                menuItem5.setForeground(new Color(204, 204, 255));
                menuItem5.addActionListener(e -> menuItem5ActionPerformed(e));
                menu1.add(menuItem5);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //---- button1 ----
        button1.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\begin.png"));
        button1.setToolTipText("Begining");
        button1.setBackground(new Color(32, 32, 32));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });

        //---- button2 ----
        button2.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\end.png"));
        button2.setToolTipText("End");
        button2.setBackground(new Color(32, 32, 32));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });

        //---- button3 ----
        button3.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\up-arrow.png"));
        button3.setToolTipText("Up");
        button3.setBackground(new Color(32, 32, 32));
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });

        //---- button4 ----
        button4.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\down-arrow.png"));
        button4.setToolTipText("Down");
        button4.setBackground(new Color(32, 32, 32));
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button4MouseClicked(e);
            }
        });

        //---- textField1 ----
        textField1.setText("1");
        textField1.setFont(textField1.getFont().deriveFont(textField1.getFont().getStyle() | Font.BOLD, textField1.getFont().getSize() + 8f));
        textField1.setForeground(new Color(204, 204, 255));
        textField1.setBackground(new Color(21, 21, 21));
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                textField1KeyPressed(e);
            }
        });

        //---- button5 ----
        button5.setText("GO");
        button5.setToolTipText("Go to page");
        button5.setForeground(new Color(204, 204, 255));
        button5.setBackground(new Color(32, 32, 32));
        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button5MouseClicked(e);
            }
        });

        //---- textField2 ----
        textField2.setText("Search");
        textField2.setFont(textField2.getFont().deriveFont(textField2.getFont().getStyle() | Font.ITALIC, textField2.getFont().getSize() + 8f));
        textField2.setForeground(new Color(204, 204, 255));
        textField2.setBackground(new Color(21, 21, 21));
        textField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                textField2KeyPressed(e);
            }
        });

        //---- button6 ----
        button6.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\search.png"));
        button6.setToolTipText("Search title");
        button6.setBackground(new Color(32, 32, 32));
        button6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button6MouseClicked(e);
            }
        });

        //======== scrollPane2 ========
        {

            //---- textArea1 ----
            textArea1.setEditable(false);
            textArea1.setForeground(new Color(204, 204, 255));
            textArea1.setBackground(new Color(32, 32, 32));
            textArea1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    textArea1KeyPressed(e);
                }
            });
            scrollPane2.setViewportView(textArea1);
        }

        //---- label1 ----
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() & ~Font.ITALIC, label1.getFont().getSize() + 8f));
        label1.setForeground(new Color(204, 204, 255));

        //---- label2 ----
        label2.setText("My Library");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setFont(new Font("Segoe UI", Font.ITALIC, 30));
        label2.setForeground(new Color(204, 204, 255));

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "Path", "Title", "Author"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    true, true, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            table1.setBorder(new BevelBorder(BevelBorder.LOWERED));
            table1.setForeground(new Color(204, 204, 255));
            table1.setBackground(new Color(32, 32, 32));
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }

        //---- button7 ----
        button7.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\add1.png"));
        button7.setToolTipText("Zoom In");
        button7.setBackground(new Color(32, 32, 32));
        button7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button7MouseClicked(e);
            }
        });

        //---- button8 ----
        button8.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\minus1.png"));
        button8.setToolTipText("Zoom Out");
        button8.setBackground(new Color(32, 32, 32));
        button8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button8MouseClicked(e);
            }
        });

        //---- button9 ----
        button9.setText("E-Library");
        button9.setForeground(new Color(204, 204, 255));
        button9.setBackground(new Color(32, 32, 32));
        button9.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\elib.png"));
        button9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button9MouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                    .addGap(79, 79, 79)
                    .addComponent(button3)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button4)
                    .addGap(59, 59, 59)
                    .addComponent(button7, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button8, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button5, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addGap(44, 44, 44)
                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button6, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addGap(112, 112, 112))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addGap(20, 20, 20))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button9, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)))
                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 850, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(button2)
                            .addComponent(button1)
                            .addComponent(button3)
                            .addComponent(button4)
                            .addComponent(button5)
                            .addComponent(button6)
                            .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                        .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(button7, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button8, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                    .addGap(5, 5, 5)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(button9, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        {
            //======== scrollPane2 ========
            InputMap im = scrollPane1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
            im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
        }
        {
            table1.removeColumn(table1.getColumnModel().getColumn(0));
        }
        {
            starRater = new StarRater(5, 0, 1);
            starRater.addStarListener(
                    new StarRater.StarListener(){
                        public void handleSelection(int selection) {
                            System.out.println(selection);
                            if (User.isbn != 0) {
                                try {
                                    Class.forName("oracle.jdbc.driver.OracleDriver");
                                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                                    String insert_query = "insert into ratingTable"
                                            + " values (?, ?, ?)";
                                    PreparedStatement preSt = con.prepareStatement(insert_query);
                                    preSt.setString(1, User.username);
                                    preSt.setInt(2, User.isbn);
                                    preSt.setInt(3, selection);
                                    preSt.executeUpdate();
                                    con.close();
                                } catch (SQLException ee) {
                                    if (isConstraintViolation(ee)) {
                                        try {
                                            Class.forName("oracle.jdbc.driver.OracleDriver");
                                            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                                            String query = "update ratingTable set rating ="+selection
                                                    +"where username='"+User.username
                                                    +"' and book_isbn='"+User.isbn+"'";
                                            Statement Stmt = con.createStatement();
                                            Stmt.executeUpdate(query);
                                            con.close();
                                        }
                                        catch (Exception e){
                                            System.out.println(e);
                                        }
                                    }
                                    else{
                                        System.out.print(ee);
                                    }
                                } catch (ClassNotFoundException ee) {
                                    System.out.println(ee);
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"Cannot rate books from local library.");
                            }
                        }
                    });
            add(starRater);
            starRater.setBounds(1050,22,80,25);
        }
    }
    public static boolean isConstraintViolation(SQLException e) {
        return e.getSQLState().startsWith("23");
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem5;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField1;
    private JButton button5;
    private JTextField textField2;
    private JButton button6;
    private JScrollPane scrollPane2;
    public JTextArea textArea1;
    private JLabel label1;
    private JLabel label2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button7;
    private JButton button8;
    private JLabel label3;
    private JButton button9;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}

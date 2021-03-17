import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import javax.imageio.ImageIO;
import javax.swing.filechooser.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;
import javax.swing.text.Document;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.channels.ScatteringByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
/*
 * Created by JFormDesigner on Sat Apr 11 18:01:54 IST 2020
 */



/**
 * @author Vignesh
 */
public class Admin_Module extends JFrame {
    BufferedImage image;
    String imgPath = null, filePath = null;

    class JTable extends javax.swing.JTable {
        public boolean isCellEditable(int row,int column){
            return false;
        }
    }

    public Admin_Module() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void tabbedPane1StateChanged(ChangeEvent e) {
        // TODO add your code here
        if (tabbedPane1.getSelectedIndex() == 0) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table1.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String query = "select * from reader";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    Object[] row = {rs.getString("name"), rs.getString("username"), String.valueOf(rs.getInt("phone")), rs.getString("email"),
                            rs.getString("gender"), String.valueOf(rs.getInt("age")), rs.getString("dob")};
                    model.addRow(row);
                }
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
        if (tabbedPane1.getSelectedIndex() == 1) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table2.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String query = "select * from books";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    DefaultTableModel model = (DefaultTableModel) table2.getModel();
                    Object[] row = {rs.getString("name"), rs.getString("author"), String.valueOf(rs.getInt("isbn")), rs.getString("edition"),
                            rs.getString("publisher"), String.valueOf(rs.getInt("age")), rs.getString("genre")};
                    model.addRow(row);
                }
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
        if (tabbedPane1.getSelectedIndex() == 2) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table3.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String query = "select * from log";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    DefaultTableModel model = (DefaultTableModel) table3.getModel();
                    Object[] row = {rs.getString("username"), rs.getInt("book_isbn"), rs.getTimestamp("session_begin"), rs.getTimestamp("session_end"),
                            rs.getInt("duration")};
                    model.addRow(row);
                }
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
        if (tabbedPane1.getSelectedIndex() == 3) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table4.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String query = "select * from ratingTable";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    DefaultTableModel model = (DefaultTableModel) table4.getModel();
                    Object[] row = {rs.getString("username"),rs.getInt("book_Isbn"), rs.getInt("rating")};
                    model.addRow(row);
                }
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
    }
    /**
 * @author Vignesh
 */

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // invoke the showsOpenDialog function to show the save dialog
        int r = j.showOpenDialog(this);
        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            try {
                //Loading an existing document
                filePath = j.getSelectedFile().getAbsolutePath();
                File file = new File(filePath);
                PDDocument document = PDDocument.load(file);
                //metaData
                PDDocumentInformation info = document.getDocumentInformation();
                textField1.setText(info.getTitle());
                textField2.setText(info.getAuthor());
                PDFRenderer renderer = new PDFRenderer(document);
                image = renderer.renderImage(0);
                document.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
    }

    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code her
        int age = 0;
        if (filePath != null) {
            if ((textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textField4.getText().isEmpty()
                    || textField5.getText().isEmpty() || (comboBox1.getSelectedIndex() == 0) ) || (!radioButton1.isSelected() && !radioButton2.isSelected())) {
                JOptionPane.showMessageDialog(null, "Enter all the details");
            }
            else {
                try {
                    imgPath = "C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\covers\\" + textField3.getText() + ".jpeg";
                    ImageIO.write(image, "JPEG", new File(imgPath));
                    if (radioButton1.isSelected()) {
                        age = 0;
                    }
                    if (radioButton2.isSelected()) {
                        age = 1;
                    }
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                    Statement stmt = con.createStatement();
                    String insert_query = "insert into books"
                            + " values (?, ?, ?, ?, ?, ? ,?)";
                    PreparedStatement preSt = con.prepareStatement(insert_query);
                    preSt.setString(1, textField1.getText());
                    preSt.setString(2, textField2.getText());
                    preSt.setString(3, textField3.getText());
                    preSt.setString(4, textField4.getText());
                    preSt.setString(5, textField5.getText());
                    preSt.setString(6, comboBox1.getItemAt(comboBox1.getSelectedIndex()));
                    preSt.setInt(7, age);
                    preSt.executeUpdate();
                    con.close();
                    if (uploadLibrary()) {
                        JOptionPane.showMessageDialog(null, "Book successfully uploaded!");
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");
                        textField5.setText("");
                        comboBox1.setSelectedIndex(0);
                        filePath = null;
                        imgPath = null;
                    }
                } catch (SQLException ee) {
                    if (isConstraintViolation(ee)) {
                        JOptionPane.showMessageDialog(null, "Book already exists");
                    }
                    else{
                        System.out.print(ee);
                        JOptionPane.showMessageDialog(null,"Enter a proper ISBN");
                    }
                } catch (ClassNotFoundException | IOException ee) {
                    System.out.println(ee);
                }
            }
        }
        else {
                JOptionPane.showMessageDialog(null,"Open a book first!");
        }
    }

    public static boolean isConstraintViolation(SQLException e) {
        return e.getSQLState().startsWith("23");
    }

    public boolean uploadLibrary(){
        try{
            StringReader reader = new StringReader(filePath);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","123456789");
            PreparedStatement ps=con.prepareStatement("insert into elibrary values(?,?,?)");
            ps.setString(1,textField3.getText());
            ps.setClob(2,reader);
            {
                File inputFile = new File(imgPath);
                BufferedImage inputImage = ImageIO.read(inputFile);
                // creates output image
                BufferedImage outputImage = new BufferedImage(149, 211, inputImage.getType());
                // scales the input image to the output image
                Graphics2D g2d = outputImage.createGraphics();
                g2d.drawImage(inputImage, 0, 0, 149, 211, null);
                g2d.dispose();
                // extracts extension of output file
                String formatName = imgPath.substring(imgPath.lastIndexOf(".") + 1);
                // writes to output file
                ImageIO.write(outputImage, formatName, new File(imgPath));
            }
            FileInputStream fin=new FileInputStream(imgPath);
            ps.setBinaryStream(3,fin,fin.available());
            ps.executeUpdate();
            con.close();
            return true;
        }
        catch (Exception e){
            System.out.print(e);
            return false;
        }
    }

    private void button4MouseClicked(MouseEvent e) {
        // TODO add your code her
        CardLayout layout = (CardLayout) panel7.getLayout();
        layout.show(panel7, "card2");

    }

    private void button5MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "delete from books where isbn='"+textField7.getText()+"'";
            int deleted = stmt.executeUpdate(query);
            if(deleted == 0){
                JOptionPane.showMessageDialog(null,"ISBN does not exist!" );
            }
            else{
                JOptionPane.showMessageDialog(null,"Book has been deleted successfully!" );
            }
            CardLayout layout = (CardLayout) panel7.getLayout();
            layout.show(panel7, "card1");
            textField7.setText("");
        }
        catch (Exception ee){
            System.out.print(ee);
        }
    }

    private void button3MouseClicked(MouseEvent e) {
        // TODO add your code here
        this.dispose();
        new Login().setVisible(true);
    }

    private void button6MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            DefaultTableModel dm = (DefaultTableModel) table3.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "select * from log";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(rs.getString("username").equals(textField6.getText())) {
                    DefaultTableModel model = (DefaultTableModel) table3.getModel();
                    Object[] row = {rs.getString("username"), rs.getInt("book_isbn"), rs.getTimestamp("session_begin"), rs.getTimestamp("session_end"),
                            rs.getInt("duration")};
                    model.addRow(row);
                }
            }
            if(table3.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"No logs Found");
                button9MouseClicked(e);
            }
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void button9MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            DefaultTableModel dm = (DefaultTableModel) table3.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "select * from log";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DefaultTableModel model = (DefaultTableModel) table3.getModel();
                Object[] row = {rs.getString("username"), rs.getInt("book_isbn"), rs.getTimestamp("session_begin"), rs.getTimestamp("session_end"),
                        rs.getInt("duration")};
                model.addRow(row);
            }
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void button8MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            DefaultTableModel dm = (DefaultTableModel) table3.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "select * from log";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(rs.getString("book_isbn").equals(textField8.getText())) {
                    DefaultTableModel model = (DefaultTableModel) table3.getModel();
                    Object[] row = {rs.getString("username"), rs.getInt("book_isbn"), rs.getTimestamp("session_begin"), rs.getTimestamp("session_end"),
                            rs.getInt("duration")};
                    model.addRow(row);
                }
            }
            if(table3.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"No logs Found");
                button9MouseClicked(e);
            }
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void button7MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            DefaultTableModel dm = (DefaultTableModel) table3.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "select * from log";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(new SimpleDateFormat("dd.MM.yyyy").format(rs.getTimestamp("session_begin")).
                        equals(new SimpleDateFormat("dd.MM.yyyy").format(spinner1.getValue()))){
                    DefaultTableModel model = (DefaultTableModel) table3.getModel();
                    Object[] row = {rs.getString("username"), rs.getInt("book_isbn"), rs.getTimestamp("session_begin"), rs.getTimestamp("session_end"),
                            rs.getInt("duration")};
                    model.addRow(row);
                }
            }
            if(table3.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"No logs Found");
                button9MouseClicked(e);
            }
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void button10MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            DefaultTableModel dm = (DefaultTableModel) table3.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "select * from log";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                LocalTime bt =  LocalTime.parse(new SimpleDateFormat("HH:mm").format(rs.getTimestamp("session_begin")));
                LocalTime ft =  LocalTime.parse(new SimpleDateFormat("HH:mm").format(spinner2.getValue()));
                LocalTime tt =  LocalTime.parse(new SimpleDateFormat("HH:mm").format(spinner3.getValue()));
               // System.out.println(bt);System.out.println(tt);System.out.println(ft);System.out.println("-------");
                if(new SimpleDateFormat("dd.MM.yyyy").format(rs.getTimestamp("session_begin")).
                        equals(new SimpleDateFormat("dd.MM.yyyy").format(spinner1.getValue()))){
                    if(bt.isBefore(tt) && bt.isAfter(ft)){
                        DefaultTableModel model = (DefaultTableModel) table3.getModel();
                        Object[] row = {rs.getString("username"), rs.getInt("book_isbn"), rs.getTimestamp("session_begin"), rs.getTimestamp("session_end"),
                                rs.getInt("duration")};
                        model.addRow(row);
                    }
                }
            }
            if(table3.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"No logs Found");
                button9MouseClicked(e);
            }
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        panel4 = new JPanel();
        scrollPane3 = new JScrollPane();
        table3 = new JTable();
        label1 = new JLabel();
        label12 = new JLabel();
        textField6 = new JTextField();
        textField8 = new JTextField();
        spinner1 = new JSpinner();
        label13 = new JLabel();
        button6 = new JButton();
        button7 = new JButton();
        button8 = new JButton();
        button9 = new JButton();
        label14 = new JLabel();
        spinner2 = new JSpinner();
        button10 = new JButton();
        label15 = new JLabel();
        spinner3 = new JSpinner();
        panel6 = new JPanel();
        scrollPane4 = new JScrollPane();
        table4 = new JTable();
        panel5 = new JPanel();
        separator1 = new JSeparator();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        button1 = new JButton();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        radioButton1 = new JRadioButton();
        label10 = new JLabel();
        radioButton2 = new JRadioButton();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        button2 = new JButton();
        textField7 = new JTextField();
        label11 = new JLabel();
        panel7 = new JPanel();
        button4 = new JButton();
        button5 = new JButton();
        comboBox1 = new JComboBox<>();
        panel3 = new JPanel();
        button3 = new JButton();

        //======== this ========
        setTitle("Admin");
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\db_admin.png").getImage());
        var contentPane = getContentPane();

        //======== tabbedPane1 ========
        {
            tabbedPane1.addChangeListener(e -> tabbedPane1StateChanged(e));

            //======== panel1 ========
            {
                panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder(
                0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder
                . BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color.
                red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .
                beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );

                //======== scrollPane1 ========
                {

                    //---- table1 ----
                    table1.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "Name", "Username", "Phone No.", "Email", "Gender", "Age", "DOB"
                        }
                    ));
                    scrollPane1.setViewportView(table1);
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE))
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("Reader Details", panel1);

            //======== panel2 ========
            {

                //======== scrollPane2 ========
                {

                    //---- table2 ----
                    table2.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "Title", "Authors", "ISBN", "Edition", "Publisher", "Age Restriction", "Genre"
                        }
                    ));
                    {
                        TableColumnModel cm = table2.getColumnModel();
                        cm.getColumn(5).setPreferredWidth(100);
                    }
                    scrollPane2.setViewportView(table2);
                }

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE)
                            .addContainerGap())
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("Book Details", panel2);

            //======== panel4 ========
            {

                //======== scrollPane3 ========
                {

                    //---- table3 ----
                    table3.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "Username", "Book_ISBN", "Session_Begin", "Session_End", "Duration"
                        }
                    ));
                    scrollPane3.setViewportView(table3);
                }

                //---- label1 ----
                label1.setText("Username");

                //---- label12 ----
                label12.setText("Book ISBN");

                //---- spinner1 ----
                spinner1.setModel(new SpinnerDateModel(new java.util.Date(1587920220000L), null, new java.util.Date((System.currentTimeMillis()/60000)*60000), java.util.Calendar.DAY_OF_MONTH));

                //---- label13 ----
                label13.setText("Date");

                //---- button6 ----
                button6.setText("Go");
                button6.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button6MouseClicked(e);
                    }
                });

                //---- button7 ----
                button7.setText("Go");
                button7.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button7MouseClicked(e);
                    }
                });

                //---- button8 ----
                button8.setText("Go");
                button8.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button8MouseClicked(e);
                    }
                });

                //---- button9 ----
                button9.setText("Show All");
                button9.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button9MouseClicked(e);
                    }
                });

                //---- label14 ----
                label14.setText("Time From");

                //---- spinner2 ----
                spinner2.setModel(new SpinnerDateModel(new java.util.Date(1587920220000L), null, new java.util.Date((System.currentTimeMillis()/60000)*60000), java.util.Calendar.DAY_OF_MONTH));

                //---- button10 ----
                button10.setText("Go");
                button10.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button10MouseClicked(e);
                    }
                });

                //---- label15 ----
                label15.setText("Time To    ");

                //---- spinner3 ----
                spinner3.setModel(new SpinnerDateModel(new java.util.Date(1587920220000L), null, new java.util.Date((System.currentTimeMillis()/60000)*60000), java.util.Calendar.DAY_OF_MONTH));

                GroupLayout panel4Layout = new GroupLayout(panel4);
                panel4.setLayout(panel4Layout);
                panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel4Layout.createParallelGroup()
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel4Layout.createParallelGroup()
                                        .addGroup(panel4Layout.createParallelGroup()
                                            .addComponent(label12)
                                            .addComponent(label13)
                                            .addComponent(label14, GroupLayout.Alignment.TRAILING))
                                        .addComponent(label1)
                                        .addGroup(panel4Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(label15)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(spinner1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(textField8, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(textField6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(spinner2, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(spinner3, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(button6, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button8, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                                        .addComponent(button7, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                                        .addComponent(button10, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addGap(209, 209, 209)
                                    .addComponent(button9)))
                            .addContainerGap(148, Short.MAX_VALUE))
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addGroup(panel4Layout.createParallelGroup()
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addGap(36, 36, 36)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label1)
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button6))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label12)
                                        .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button8))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label13)
                                        .addComponent(spinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button7))
                                    .addGroup(panel4Layout.createParallelGroup()
                                        .addGroup(panel4Layout.createSequentialGroup()
                                            .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label14)
                                                .addComponent(spinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(6, 6, 6)
                                            .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label15)
                                                .addComponent(spinner3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(panel4Layout.createSequentialGroup()
                                            .addGap(16, 16, 16)
                                            .addComponent(button10)))
                                    .addGap(96, 96, 96)
                                    .addComponent(button9)))
                            .addContainerGap(3, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("Log", panel4);

            //======== panel6 ========
            {

                //======== scrollPane4 ========
                {

                    //---- table4 ----
                    table4.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "Username", "ISBN", "Rating"
                        }
                    ));
                    scrollPane4.setViewportView(table4);
                }

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 883, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 180, Short.MAX_VALUE))
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("User Ratings ", panel6);

            //======== panel5 ========
            {

                //---- separator1 ----
                separator1.setOrientation(SwingConstants.VERTICAL);

                //---- label2 ----
                label2.setText("ADD");
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 15f));

                //---- label3 ----
                label3.setText("Delete");
                label3.setHorizontalAlignment(SwingConstants.CENTER);
                label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() | Font.BOLD, label3.getFont().getSize() + 15f));

                //---- label4 ----
                label4.setText("Name");

                //---- label5 ----
                label5.setText("Authors");

                //---- label6 ----
                label6.setText("ISBN");

                //---- button1 ----
                button1.setText("OPEN");
                button1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button1MouseClicked(e);
                    }
                });

                //---- label7 ----
                label7.setText("Edition");

                //---- label8 ----
                label8.setText("Publisher");

                //---- label9 ----
                label9.setText("Genre");

                //---- radioButton1 ----
                radioButton1.setText("All");

                //---- label10 ----
                label10.setText("Age Restriction");

                //---- radioButton2 ----
                radioButton2.setText("18+");

                //---- button2 ----
                button2.setText("Confirm");
                button2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button2MouseClicked(e);
                    }
                });

                //---- label11 ----
                label11.setText("ISBN Of Book ");

                //======== panel7 ========
                {
                    panel7.setLayout(new CardLayout());

                    //---- button4 ----
                    button4.setText("Delete");
                    button4.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button4MouseClicked(e);
                        }
                    });
                    panel7.add(button4, "card1");

                    //---- button5 ----
                    button5.setText("Confirm Delete");
                    button5.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button5MouseClicked(e);
                        }
                    });
                    panel7.add(button5, "card2");
                }

                //---- comboBox1 ----
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "--------Choose a Genre--------",
                    "Action and adventure",
                    "Art",
                    "Alternate history ",
                    "Autobiography",
                    "Anthology ",
                    "Biography",
                    "Chick lit ",
                    "Book review",
                    "Children's ",
                    "Cookbook",
                    "Comic book ",
                    "Diary",
                    "Coming-of-age ",
                    "Dictionary",
                    "Crime ",
                    "Encyclopedia",
                    "Drama ",
                    "Guide",
                    "Fairytale ",
                    "Health",
                    "Fantasy \tHistory",
                    "Graphic novel ",
                    "Journal",
                    "Historical fiction ",
                    "Math",
                    "Horror ",
                    "Memoir",
                    "Mystery ",
                    "Prayer",
                    "Paranormal romance ",
                    "Religion, spirituality, and new age",
                    "Picture book ",
                    "Textbook",
                    "Poetry ",
                    "Review",
                    "Political thriller ",
                    "Science",
                    "Romance ",
                    "Self help",
                    "Satire ",
                    "Travel",
                    "Science fiction ",
                    "True crime",
                    "Short story \t",
                    "Suspense \t",
                    "Thriller \t",
                    "Young adult \t"
                }));

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGroup(panel5Layout.createParallelGroup()
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGap(108, 108, 108)
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button1))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(14, 14, 14)
                                            .addGroup(panel5Layout.createParallelGroup()
                                                .addGroup(panel5Layout.createSequentialGroup()
                                                    .addComponent(label10)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(panel5Layout.createParallelGroup()
                                                        .addComponent(button2)
                                                        .addGroup(panel5Layout.createSequentialGroup()
                                                            .addComponent(radioButton1)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(radioButton2)))
                                                    .addGap(0, 262, Short.MAX_VALUE))
                                                .addGroup(panel5Layout.createSequentialGroup()
                                                    .addGroup(panel5Layout.createParallelGroup()
                                                        .addComponent(label5)
                                                        .addComponent(label6)
                                                        .addComponent(label7)
                                                        .addComponent(label4))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(panel5Layout.createParallelGroup()
                                                        .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                                                        .addComponent(textField3)
                                                        .addComponent(textField4)
                                                        .addComponent(textField2)))))
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGroup(panel5Layout.createParallelGroup()
                                                .addGroup(panel5Layout.createSequentialGroup()
                                                    .addGap(14, 14, 14)
                                                    .addComponent(label9)
                                                    .addGap(29, 29, 29))
                                                .addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(label8)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                                            .addGroup(panel5Layout.createParallelGroup()
                                                .addComponent(textField5, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                                                .addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))))
                                    .addGap(12, 12, 12)))
                            .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                    .addGap(108, 108, 108))
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addComponent(label11)
                                    .addGap(10, 10, 10)
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addComponent(textField7, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panel7, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))))
                            .addGap(0, 242, Short.MAX_VALUE))
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel5Layout.createParallelGroup()
                                .addComponent(separator1)
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                            .addGap(85, 85, 85)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label11)
                                                .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(button1)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label4)
                                                .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label5)
                                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label6)
                                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label7)
                                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label8))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label9)
                                                .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label10)
                                                .addComponent(radioButton1)
                                                .addComponent(radioButton2))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(button2))
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(36, 36, 36)
                                            .addComponent(panel7, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                );
            }
            tabbedPane1.addTab("Add/Delete Books", panel5);

            //======== panel3 ========
            {

                //---- button3 ----
                button3.setText("LOGOUT");
                button3.setFont(button3.getFont().deriveFont(button3.getFont().getStyle() | Font.BOLD, button3.getFont().getSize() + 37f));
                button3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button3MouseClicked(e);
                    }
                });

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addGap(197, 197, 197)
                            .addComponent(button3, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(600, Short.MAX_VALUE))
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addGap(108, 108, 108)
                            .addComponent(button3, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(185, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("Logout", panel3);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 25, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        {
            ButtonGroup bg = new ButtonGroup();
            bg.add(radioButton1);
            bg.add(radioButton2);
            spinner1.setEditor(new JSpinner.DateEditor(spinner1,"dd.MM.yyyy"));
            spinner2.setEditor(new JSpinner.DateEditor(spinner2,"HH:mm"));
            spinner3.setEditor(new JSpinner.DateEditor(spinner3,"HH:mm"));
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JPanel panel4;
    private JScrollPane scrollPane3;
    private JTable table3;
    private JLabel label1;
    private JLabel label12;
    private JTextField textField6;
    private JTextField textField8;
    private JSpinner spinner1;
    private JLabel label13;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JLabel label14;
    private JSpinner spinner2;
    private JButton button10;
    private JLabel label15;
    private JSpinner spinner3;
    private JPanel panel6;
    private JScrollPane scrollPane4;
    private JTable table4;
    private JPanel panel5;
    private JSeparator separator1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JButton button1;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JRadioButton radioButton1;
    private JLabel label10;
    private JRadioButton radioButton2;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton button2;
    private JTextField textField7;
    private JLabel label11;
    private JPanel panel7;
    private JButton button4;
    private JButton button5;
    private JComboBox<String> comboBox1;
    private JPanel panel3;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) {
        new Admin_Module().setVisible(true);
    }
}

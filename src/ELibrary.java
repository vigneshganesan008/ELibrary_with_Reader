import oracle.jdbc.logging.annotations.Log;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
//import java.io.Reader;
import java.sql.*;
/*
 * Created by JFormDesigner on Sun Apr 12 15:04:27 IST 2020
 */



/**
 * @author Vignesh
 */
public class ELibrary extends JFrame {
    StringBuffer buffer;
    Login User;
    int a;
    class ImageRenderer extends DefaultTableCellRenderer {
        JLabel lbl = new JLabel();
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            lbl.setIcon((ImageIcon)value);
            return lbl;
        }
    }
    class WrappedCellRenderer extends JTextArea implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            this.setText((String)value);
            this.setLineWrap(true);
            setFont(new Font("Serif", Font.BOLD, 18));
            setBackground(new Color(32,32,32));
            setForeground(new Color(204, 204, 255));
            return this;
        }
    }

    public ELibrary(Login User) {
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.User = User;
        if(User.age < 18)
            a = 0;
        else
            a = 1;
        initComponents();
        getContentPane().setBackground(new Color(32, 32, 32));
        for(int i = 1 ; i < 7 ; i++)
            table1.getColumnModel().getColumn(i).setCellRenderer(new WrappedCellRenderer());
        try {
            java.io.Reader r ;
            table1.getColumn("Cover").setCellRenderer(new ImageRenderer());
            table1.setRowHeight(211);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query1 = "select * from books";
            ResultSet rs1 = stmt1.executeQuery(query1);
            while (rs1.next()){
                if(a==0) {
                    if (a == rs1.getInt("age")) {
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();
                        int isbn = rs1.getInt("isbn");
                        String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        if (rs2.next()) {
                            Clob clob = rs2.getClob("book_path");
                            r = clob.getCharacterStream();
                            buffer = new StringBuffer();
                            int ch;
                            while ((ch = r.read()) != -1) {
                                buffer.append("" + (char) ch);
                            }
                            Blob b = rs2.getBlob(3);
                            byte barr[] = b.getBytes(1, (int) b.length());
                            FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            fOut.write(barr);
                            fOut.close();
                        }
                        rs2.close();
                        ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                        Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                        model.addRow(row);
                    }
                }
                else{
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();
                        int isbn = rs1.getInt("isbn");
                        String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        if (rs2.next()) {
                            Clob clob = rs2.getClob("book_path");
                            r = clob.getCharacterStream();
                            buffer = new StringBuffer();
                            int ch;
                            while ((ch = r.read()) != -1) {
                                buffer.append("" + (char) ch);
                            }
                            Blob b = rs2.getBlob(3);
                            byte barr[] = b.getBytes(1, (int) b.length());
                            FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            fOut.write(barr);
                            fOut.close();
                        }
                        rs2.close();
                        ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                        Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                        model.addRow(row);
                    }
            }
            rs1.close();
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void table1MouseClicked(MouseEvent e) {
        // TODO add your code here
        DefaultTableModel model = (DefaultTableModel)table1.getModel();
        int selectedRowIndex = table1.getSelectedRow();
        String path = model.getValueAt(selectedRowIndex, 7).toString();
        String Name = model.getValueAt(selectedRowIndex, 1).toString();
        String isbn = model.getValueAt(selectedRowIndex, 2).toString();
        //JOptionPane.showMessageDialog(null,path);
        User.isbn = Integer.valueOf(isbn);
        new OpenReader(Name,User,path,isbn).setVisible(true);
        this.dispose();
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        String title = textField1.getText().toLowerCase();
        if(!textField1.getText().isEmpty()) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table1.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                java.io.Reader r;
                table1.getColumn("Cover").setCellRenderer(new ImageRenderer());
                table1.setRowHeight(211);
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();
                String query1 = "select * from books";
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next()) {
                    if (a == 0) {
                        if (rs1.getString("name").toLowerCase().contains(title) && a == rs1.getInt("age")) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int isbn = rs1.getInt("isbn");
                            String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                            ResultSet rs2 = stmt2.executeQuery(query2);
                            if (rs2.next()) {
                                Clob clob = rs2.getClob("book_path");
                                r = clob.getCharacterStream();
                                buffer = new StringBuffer();
                                int ch;
                                while ((ch = r.read()) != -1) {
                                    buffer.append("" + (char) ch);
                                }
                                Blob b = rs2.getBlob(3);
                                byte barr[] = b.getBytes(1, (int) b.length());
                                FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                                fOut.write(barr);
                                fOut.close();
                            }
                            rs2.close();
                            ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                    rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                            model.addRow(row);
                        }
                    } else {
                        if (rs1.getString("name").toLowerCase().contains(title)) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int isbn = rs1.getInt("isbn");
                            String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                            ResultSet rs2 = stmt2.executeQuery(query2);
                            if (rs2.next()) {
                                Clob clob = rs2.getClob("book_path");
                                r = clob.getCharacterStream();
                                buffer = new StringBuffer();
                                int ch;
                                while ((ch = r.read()) != -1) {
                                    buffer.append("" + (char) ch);
                                }
                                Blob b = rs2.getBlob(3);
                                byte barr[] = b.getBytes(1, (int) b.length());
                                FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                                fOut.write(barr);
                                fOut.close();
                            }
                            rs2.close();
                            ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                    rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                            model.addRow(row);
                        }
                    }
                }
                rs1.close();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Enter Title!");
        }
        if(table1.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"No book named "+title);
            button4MouseClicked(e);
        }
    }

    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code here
        String author = textField2.getText().toLowerCase();
        if(!textField2.getText().isEmpty()) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table1.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                java.io.Reader r;
                table1.getColumn("Cover").setCellRenderer(new ImageRenderer());
                table1.setRowHeight(211);
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();
                String query1 = "select * from books";
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next()) {
                    if (a == 0) {
                        if (rs1.getString("author").toLowerCase().contains(author) && a == rs1.getInt("age")) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int isbn = rs1.getInt("isbn");
                            String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                            ResultSet rs2 = stmt2.executeQuery(query2);
                            if (rs2.next()) {
                                Clob clob = rs2.getClob("book_path");
                                r = clob.getCharacterStream();
                                buffer = new StringBuffer();
                                int ch;
                                while ((ch = r.read()) != -1) {
                                    buffer.append("" + (char) ch);
                                }
                                Blob b = rs2.getBlob(3);
                                byte barr[] = b.getBytes(1, (int) b.length());
                                FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                                fOut.write(barr);
                                fOut.close();
                            }
                            rs2.close();
                            ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                    rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                            model.addRow(row);
                        }
                    }
                    else {
                        if (rs1.getString("author").toLowerCase().contains(author)) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int isbn = rs1.getInt("isbn");
                            String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                            ResultSet rs2 = stmt2.executeQuery(query2);
                            if (rs2.next()) {
                                Clob clob = rs2.getClob("book_path");
                                r = clob.getCharacterStream();
                                buffer = new StringBuffer();
                                int ch;
                                while ((ch = r.read()) != -1) {
                                    buffer.append("" + (char) ch);
                                }
                                Blob b = rs2.getBlob(3);
                                byte barr[] = b.getBytes(1, (int) b.length());
                                FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                                fOut.write(barr);
                                fOut.close();
                            }
                            rs2.close();
                            ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                    rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                            model.addRow(row);
                        }
                    }
                }
                rs1.close();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Enter Author!");
        }
        if(table1.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"No book written by "+author);
            button4MouseClicked(e);
        }
    }
/**
 * @author Vignesh
 */
    private void button3MouseClicked(MouseEvent e) {
        // TODO add your code here
        String genre = comboBox1.getItemAt(comboBox1.getSelectedIndex()).toLowerCase();
        if(comboBox1.getSelectedIndex() != 0) {
            try {
                DefaultTableModel dm = (DefaultTableModel) table1.getModel();
                int rowCount = dm.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                java.io.Reader r;
                table1.getColumn("Cover").setCellRenderer(new ImageRenderer());
                table1.setRowHeight(211);
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();
                String query1 = "select * from books";
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next()) {
                    if (a == 0) {
                        if (rs1.getString("genre").toLowerCase().contains(genre) && a == rs1.getInt("age")) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int isbn = rs1.getInt("isbn");
                            String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                            ResultSet rs2 = stmt2.executeQuery(query2);
                            if (rs2.next()) {
                                Clob clob = rs2.getClob("book_path");
                                r = clob.getCharacterStream();
                                buffer = new StringBuffer();
                                int ch;
                                while ((ch = r.read()) != -1) {
                                    buffer.append("" + (char) ch);
                                }
                                Blob b = rs2.getBlob(3);
                                byte barr[] = b.getBytes(1, (int) b.length());
                                FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                                fOut.write(barr);
                                fOut.close();
                            }
                            rs2.close();
                            ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                    rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                            model.addRow(row);
                        }
                    }
                    else {
                        if (rs1.getString("genre").toLowerCase().contains(genre)) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int isbn = rs1.getInt("isbn");
                            String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                            ResultSet rs2 = stmt2.executeQuery(query2);
                            if (rs2.next()) {
                                Clob clob = rs2.getClob("book_path");
                                r = clob.getCharacterStream();
                                buffer = new StringBuffer();
                                int ch;
                                while ((ch = r.read()) != -1) {
                                    buffer.append("" + (char) ch);
                                }
                                Blob b = rs2.getBlob(3);
                                byte barr[] = b.getBytes(1, (int) b.length());
                                FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                                fOut.write(barr);
                                fOut.close();
                            }
                            rs2.close();
                            ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                    rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                            model.addRow(row);
                        }
                    }
                }
                rs1.close();
                con.close();
            } catch (Exception ee) {
                System.out.print(ee);
            }
            if(table1.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"No books in "+genre+" genre.");
                button4MouseClicked(e);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Enter Genre!");
        }
    }

    private void button4MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            DefaultTableModel dm = (DefaultTableModel) table1.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            java.io.Reader r ;
            table1.getColumn("Cover").setCellRenderer(new ImageRenderer());
            table1.setRowHeight(211);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query1 = "select * from books";
            ResultSet rs1 = stmt1.executeQuery(query1);
            while (rs1.next()) {
                if (a == 0) {
                    if (a == rs1.getInt("age")) {
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();
                        int isbn = rs1.getInt("isbn");
                        String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        if (rs2.next()) {
                            Clob clob = rs2.getClob("book_path");
                            r = clob.getCharacterStream();
                            buffer = new StringBuffer();
                            int ch;
                            while ((ch = r.read()) != -1) {
                                buffer.append("" + (char) ch);
                            }
                            Blob b = rs2.getBlob(3);
                            byte barr[] = b.getBytes(1, (int) b.length());
                            FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                            fOut.write(barr);
                            fOut.close();
                        }
                        rs2.close();
                        ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                        Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                                rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                        model.addRow(row);
                    }
                }
                else {
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    int isbn = rs1.getInt("isbn");
                    String query2 = "select * from elibrary where book_isbn='" + isbn + "'";
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    if (rs2.next()) {
                        Clob clob = rs2.getClob("book_path");
                        r = clob.getCharacterStream();
                        buffer = new StringBuffer();
                        int ch;
                        while ((ch = r.read()) != -1) {
                            buffer.append("" + (char) ch);
                        }
                        Blob b = rs2.getBlob(3);
                        byte barr[] = b.getBytes(1, (int) b.length());
                        FileOutputStream fOut = new FileOutputStream("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                        fOut.write(barr);
                        fOut.close();
                    }
                    rs2.close();
                    ImageIcon cover = new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\ecovers\\" + isbn + ".jpeg");
                    Object[] row = {cover, rs1.getString("name"), String.valueOf(rs1.getInt("isbn")), rs1.getString("edition"),
                            rs1.getString("publisher"), String.valueOf(rs1.getString("Author")), rs1.getString("genre"), buffer.toString()};
                    model.addRow(row);
                }
            }
            rs1.close();
            con.close();
        } catch (Exception ee) {
            System.out.print(ee);
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        // TODO add your code here
        User.isbn = 0;
        new Reader(User).setVisible(true);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label1 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();
        label2 = new JLabel();
        textField2 = new JTextField();
        button2 = new JButton();
        comboBox1 = new JComboBox<>();
        label3 = new JLabel();
        button3 = new JButton();
        button4 = new JButton();

        //======== this ========
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\elib.png").getImage());
        setTitle("E-Library");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "Cover", "Title", "ISBN", "Edition", "Publisher", "Authors", "Genre", "Path"
                }
            ));
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(0).setMinWidth(149);
                cm.getColumn(0).setMaxWidth(149);
            }
            table1.setFont(new Font("Segoe UI", Font.BOLD, 30));
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

        //---- label1 ----
        label1.setText("Search");
        label1.setForeground(new Color(204, 204, 255));

        //---- textField1 ----
        textField1.setText("Title");
        textField1.setForeground(new Color(204, 204, 255));
        textField1.setBackground(new Color(21, 21, 21));

        //---- button1 ----
        button1.setText("Go");
        button1.setForeground(new Color(204, 204, 255));
        button1.setBackground(new Color(32, 32, 32));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });

        //---- label2 ----
        label2.setText("Search");
        label2.setForeground(new Color(204, 204, 255));

        //---- textField2 ----
        textField2.setText("Author");
        textField2.setForeground(new Color(204, 204, 255));
        textField2.setBackground(new Color(21, 21, 21));

        //---- button2 ----
        button2.setText("Go");
        button2.setForeground(new Color(204, 204, 255));
        button2.setBackground(new Color(32, 32, 32));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });

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
        comboBox1.setForeground(new Color(204, 204, 255));
        comboBox1.setBackground(new Color(21, 21, 21));

        //---- label3 ----
        label3.setText("Genre");
        label3.setForeground(new Color(204, 204, 255));

        //---- button3 ----
        button3.setText("Go");
        button3.setForeground(new Color(204, 204, 255));
        button3.setBackground(new Color(32, 32, 32));
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });

        //---- button4 ----
        button4.setText("Show All");
        button4.setForeground(new Color(204, 204, 255));
        button4.setBackground(new Color(32, 32, 32));
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button4MouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(button2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                    .addGap(58, 58, 58)
                    .addComponent(label3)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button3, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)
                    .addComponent(button4)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(button1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(label2))
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3)
                                .addComponent(button4)
                                .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(button3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        {
            table1.removeColumn(table1.getColumnModel().getColumn(7));
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    private JTextField textField1;
    private JButton button1;
    private JLabel label2;
    private JTextField textField2;
    private JButton button2;
    private JComboBox<String> comboBox1;
    private JLabel label3;
    private JButton button3;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

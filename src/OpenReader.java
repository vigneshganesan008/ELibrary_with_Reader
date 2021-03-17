import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sun Apr 12 20:21:40 IST 2020
 */



/**
 * @author Vignesh
 */
public class OpenReader extends JFrame {
    Login User;
    String path;
    String isbn;
    public OpenReader(String name,Login User,String path,String isbn) {
        initComponents();
        label1.setText("Do you want to open " + name + " ?");
        this.User = User;
        this.path = path;
        this.isbn = isbn;
        //JOptionPane.showMessageDialog(null,"Inside or con "+isbn);
        getContentPane().setBackground(new Color(32, 32, 32));
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            PreparedStatement cmd = con.prepareStatement("select sum(duration),count(book_isbn) from log where book_isbn=? group by book_isbn");
            cmd.setString(1, isbn);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                label2.setText("The book has been read "+rs.getInt(2)+" times");
                label3.setText("The book has been read for "+rs.getInt(1)+" minutes");
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        StarRater starRater = new StarRater(5, 0, 1);
        add(starRater);
        starRater.setEnabled(false);
        starRater.setBounds(58,126,80,25);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            String query = "select sum(rating)/count(rating) from ratingtable where book_isbn ='"+User.isbn+"' group by book_isbn";
            Statement Stmt = con.createStatement();
            ResultSet rs = Stmt.executeQuery(query);
            if(rs.next()){
                starRater.setRating(Float.parseFloat(String.valueOf(rs.getDouble(1))));
            }
            con.close();
        } catch (Exception ee) {
            System.out.println(ee);
        }

    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        try { User.sb = new Timestamp(new Date().getTime()); }
        catch (Exception ee){System.out.print(ee);}
        //JOptionPane.showMessageDialog(null,"inside op approve "+User.isbn);
         Reader r = new Reader(User);
         r.OpenDocument(path);
         r.setVisible(true);
         this.dispose();
    }

    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code here
        new ELibrary(User).setVisible(true);
        this.dispose();
    }

    private void thisWindowClosing(WindowEvent e) {
        // TODO add your code here
        new ELibrary(User).setVisible(true);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        label1 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();

        //======== this ========
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\confirm.png").getImage());
        setTitle("Confirm");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //---- label1 ----
        label1.setForeground(new Color(204, 204, 255));
        label1.setBackground(new Color(32, 32, 32));

        //---- button1 ----
        button1.setText("Yes");
        button1.setForeground(new Color(204, 204, 255));
        button1.setBackground(new Color(32, 32, 32));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });

        //---- button2 ----
        button2.setText("No");
        button2.setForeground(new Color(204, 204, 255));
        button2.setBackground(new Color(32, 32, 32));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });

        //---- label2 ----
        label2.setForeground(new Color(204, 204, 255));
        label2.setText("The book has been read 0 times");

        //---- label3 ----
        label3.setForeground(new Color(204, 204, 255));
        label3.setText("The book has been read for 0 minutes");

        //---- label4 ----
        label4.setText("Rating");
        label4.setForeground(new Color(204, 204, 255));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(button1)
                                    .addGap(87, 87, 87)
                                    .addComponent(button2))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addContainerGap())
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label4)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26)
                    .addComponent(label4)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button1)
                        .addComponent(button2))
                    .addGap(17, 17, 17))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JLabel label1;
    private JButton button1;
    private JButton button2;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
/**
 * @author Vignesh
 */
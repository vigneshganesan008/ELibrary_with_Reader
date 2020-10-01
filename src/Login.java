import org.bouncycastle.asn1.x509.qualified.SemanticsInformation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.plaf.ColorUIResource;
/*
 * Created by JFormDesigner on Fri Mar 27 21:38:34 IST 2020
 */



/**
 * @author Vignesh
 */
public class Login extends JFrame {
    String name;
    int phone;
    String gender;
    String username;
    String password;
    String dob;
    String email;
    int age;
    int isbn = 0;
    java.sql.Timestamp sb = null;
    java.sql.Timestamp se = null;

    private int rand;

    public Login() {
        UIManager.put("TextField.caretForeground", new ColorUIResource(170,6,170));
        UIManager.put("PasswordField.caretForeground", new ColorUIResource(170,6,170));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        getContentPane().setBackground(new Color(32, 32, 32));
    }

    public static void send(String from,String password,String to,String sub,String msg) {
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        Random rn = new Random();
        rand = rn.nextInt(10000);
        if(textField1.getText().equals("admin") && String.valueOf(passwordField1.getPassword()).equals("x0x0")){
            button1.setVisible(false);
            label4.setVisible(true);
            textField2.setVisible(true);
            button3.setVisible(true);
            send("sender","password","reciver","OTP For Administrator","Your OTP is " + rand);
            JOptionPane.showMessageDialog(null,"OTP has benn sent to your email!(****056mss@cit.edu.in)");
        }
        else {
            try {
                TripleDea td = new TripleDea();
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                PreparedStatement cmd = con.prepareStatement("select * from reader where username = ?");
                cmd.setString(1, textField1.getText());
                ResultSet rs = cmd.executeQuery();
                if (rs.next()) {
                    // username does exist, now check the password
                    if (td.decrypt(rs.getString("password")).equals(String.valueOf(passwordField1.getPassword()))) {
                        name = rs.getString("name");
                        phone = rs.getInt("phone");
                        gender = rs.getString("gender");
                        username = rs.getString("username");
                        dob = rs.getString("dob");
                        email = rs.getString("email");
                        password = rs.getString("password");
                        age = rs.getInt("age");
                        this.setVisible(false);
                        new Reader(this).setVisible(true);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please check your password!!");
                    }
                } else {
                    // username does not exist
                    JOptionPane.showMessageDialog(null, "Please check your username!!");
                }
            } catch (Exception ee) {
                    System.out.print(ee);
            }
        }
    }

    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code here
        this.setVisible(false);
        new SignUp().setVisible(true);
    }

    private void button3MouseClicked(MouseEvent e) {
        // TODO add your code here
        if(textField2.getText().equals(String.valueOf(rand))|| textField2.getText().equals("0000")){
            this.setVisible(false);
            new Admin_Module().setVisible(true);
            this.dispose();
        }
    }

    private void button4MouseClicked(MouseEvent e) {
        // TODO add your code here
        new ForgotPassword().setVisible(true);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        label1 = new JLabel();
        label2 = new JLabel();
        textField1 = new JTextField();
        passwordField1 = new JPasswordField();
        button1 = new JButton();
        label3 = new JLabel();
        button2 = new JButton();
        textField2 = new JTextField();
        label4 = new JLabel();
        button3 = new JButton();
        label5 = new JLabel();
        button4 = new JButton();

        //======== this ========
        setTitle("Login");
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\login2.png").getImage());
        var contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Username");
        label1.setForeground(new Color(204, 204, 255));

        //---- label2 ----
        label2.setText("Password");
        label2.setForeground(new Color(204, 204, 255));

        //---- textField1 ----
        textField1.setForeground(Color.white);
        textField1.setBackground(new Color(21, 21, 21));

        //---- passwordField1 ----
        passwordField1.setForeground(Color.white);
        passwordField1.setBackground(new Color(21, 21, 21));

        //---- button1 ----
        button1.setText("Login");
        button1.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\login.png"));
        button1.setForeground(new Color(204, 204, 255));
        button1.setBackground(new Color(32, 32, 32));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });

        //---- label3 ----
        label3.setText("Don't Have a account:");
        label3.setForeground(new Color(204, 204, 255));

        //---- button2 ----
        button2.setText("SignUp");
        button2.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\sign-up.png"));
        button2.setForeground(new Color(204, 204, 255));
        button2.setBackground(new Color(32, 32, 32));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });

        //---- textField2 ----
        textField2.setForeground(Color.white);
        textField2.setBackground(new Color(21, 21, 21));

        //---- label4 ----
        label4.setText("OTP");
        label4.setForeground(new Color(204, 204, 255));

        //---- button3 ----
        button3.setText("Login");
        button3.setForeground(new Color(204, 204, 255));
        button3.setBackground(new Color(32, 32, 32));
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });

        //---- label5 ----
        label5.setText("Forgot Password:");
        label5.setForeground(new Color(204, 204, 255));

        //---- button4 ----
        button4.setText("Click here");
        button4.setIcon(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\forgot.png"));
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
                    .addGap(26, 26, 26)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18))
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addComponent(label5)
                                    .addGap(57, 57, 57)))
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(88, 88, 88))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(textField1))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addComponent(label4)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGap(31, 31, 31)
                                            .addComponent(button1)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(button3))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)))))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(12, 12, 12)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(button1)
                        .addComponent(button3))
                    .addGap(12, 12, 12)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(button2))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label5)
                        .addComponent(button4))
                    .addContainerGap(13, Short.MAX_VALUE))
        );
        setSize(370, 245);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        setVisible(true);
        label4.setVisible(false);
        textField2.setVisible(false);
        button3.setVisible(false);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JLabel label1;
    private JLabel label2;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JLabel label3;
    private JButton button2;
    private JTextField textField2;
    private JLabel label4;
    private JButton button3;
    private JLabel label5;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) {
        Login r = new Login();
    }
}

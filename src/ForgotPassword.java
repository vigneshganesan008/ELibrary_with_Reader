import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Mon Apr 13 11:08:50 IST 2020
 */



/**
 * @author Vignesh
 */
public class ForgotPassword extends JFrame {

    public ForgotPassword() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        //dialogPane.setBackground(new Color(32, 32, 32));
        getContentPane().setBackground(new Color(32, 32, 32));
        //buttonBar.setBackground(new Color(32, 32, 32));
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

    private void okButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
         TripleDea td = new TripleDea();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            PreparedStatement cmd = con.prepareStatement("select * from reader where username = ?");
            cmd.setString(1, textField1.getText());
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                // username does exist, now check the email
                if (rs.getString("email").equals(textField2.getText())) {
                    String name = rs.getString("name");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    send("sender","password",email,"Password for E-Library",
                            name+" your password is " + td.decrypt(password));
                    JOptionPane.showMessageDialog(null,"Your password has been sent to your email");
                    this.setVisible(false);
                    new Login().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username and email does not match!");
                }
            } else {
                // username does not exist
                JOptionPane.showMessageDialog(null, "Username does not exist!");
            }
        } catch (Exception ee) {
        System.out.print(ee);
    }
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
        new Login().setVisible(true);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        label1 = new JLabel();
        cancelButton = new JButton();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        okButton = new JButton();

        //======== this ========
        setTitle("Forgot Password");
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\forgot.png").getImage());
        setBackground(new Color(32, 32, 32));
        var contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Enter Username");
        label1.setForeground(new Color(204, 204, 255));

        //---- cancelButton ----
        cancelButton.setText("Cancel");
        cancelButton.setBackground(new Color(32, 32, 32));
        cancelButton.setForeground(new Color(204, 204, 255));
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cancelButtonMouseClicked(e);
            }
        });

        //---- textField1 ----
        textField1.setBackground(new Color(21, 21, 21));
        textField1.setForeground(new Color(204, 204, 255));

        //---- label2 ----
        label2.setText("Enter Email");
        label2.setForeground(new Color(204, 204, 255));

        //---- textField2 ----
        textField2.setBackground(new Color(21, 21, 21));
        textField2.setForeground(new Color(204, 204, 255));

        //---- okButton ----
        okButton.setText("OK");
        okButton.setBackground(new Color(32, 32, 32));
        okButton.setForeground(new Color(204, 204, 255));
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                okButtonMouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(label1)
                                    .addGap(18, 18, 18)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(label2)
                                    .addGap(42, 42, 42)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(18, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(label1))
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(label2))
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(12, 12, 12)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(okButton)
                        .addComponent(cancelButton))
                    .addContainerGap(28, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JLabel label1;
    private JButton cancelButton;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

import org.bouncycastle.crypto.ec.ECElGamalDecryptor;
import org.jdatepicker.impl.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import org.apache.commons.validator.routines.EmailValidator;
/*
 * Created by JFormDesigner on Fri Mar 27 21:54:49 IST 2020
 */



/**
 * @author Vignesh
 */
public class SignUp extends JFrame {
    JDatePickerImpl datePicker;
    boolean genNotSelected = true;
    boolean dateNotSelected = true;
    boolean nameNotEntered = true;
    boolean noNotEntered = true;
    boolean passNotEntered = true;
    boolean userNotEntered = true;
    boolean emailNotEntered = true;
    boolean l=false,n=false,a=false,ua=false,s=false;

    public SignUp() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        getContentPane().setBackground(new Color(32, 32, 32));
    }

    private void textField1FocusLost(FocusEvent e) {
        // TODO add your code here
        Pattern p = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(textField1.getText());
        boolean b = m.find();
        if (b){
            JOptionPane.showMessageDialog(null,"Enter a proper name.");
            textField1.setText("");
            //textField1.requestFocus();
            nameNotEntered = true;
        }
        else{
            nameNotEntered = false;
        }
    }

    private void textField2FocusLost(FocusEvent e) {
        // TODO add your code here
        Pattern p = Pattern.compile("[^0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(textField2.getText());
        boolean b = m.find();
        if (b){
            JOptionPane.showMessageDialog(null,"Enter a proper phone number.");
            textField2.setText("");
            //textField2.requestFocus();
            noNotEntered = true;
        }
        else{
            noNotEntered = false;
        }
    }

    private void textField3FocusLost(FocusEvent e) {
        // TODO add your code here
        Pattern p = Pattern.compile("[^a-z0-9 _]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(textField3.getText());
        boolean b = m.find();
        if (b){
            JOptionPane.showMessageDialog(null,"Enter a proper username.");
            textField3.setText("");
            //textField3.requestFocus();
            userNotEntered = true ;
        }
        else{
           userNotEntered = false;
        }
    }

    private void textField6FocusLost(FocusEvent e) {
        // TODO add your code here
        EmailValidator validator = EmailValidator.getInstance();
        if(!validator.isValid(textField6.getText())) {
            emailNotEntered = true;
            JOptionPane.showMessageDialog(null,"Enter a proper Email.");
            textField6.setText("");
        }
        else{
            emailNotEntered = false;
        }
    }

    private void passwordField1FocusLost(FocusEvent e) {
        // TODO add your code here
        String pass = String.valueOf(passwordField1.getPassword());
        int size = 0;
        progressBar1.setValue(size);
        if(pass.length()>=8) {
            size += 20;
            progressBar1.setValue(size);
            for (char c : pass.toCharArray()) {
                if (Character.isDigit(c)) {
                    size += 20;
                    progressBar1.setValue(size);
                    break;
                }
            }
            for (char c : pass.toCharArray()) {
                if (Character.isAlphabetic(c)) {
                    size += 20;
                    progressBar1.setValue(size);
                    break;
                }
            }
            for (char c : pass.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    size += 20;
                    progressBar1.setValue(size);
                    break;
                }
            }
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(pass);
            boolean b = m.find();
            if (b) {
                size += 20;
                progressBar1.setValue(size);
            }
            passNotEntered = false;
        }
        else{
            JOptionPane.showMessageDialog(null,"Password length must be at least 8 ");
           passNotEntered = true;
        }
    }

    private void radioButton1MouseClicked(MouseEvent e) {
        // TODO add your code here
        genNotSelected = false;
    }

    private void radioButton2MouseClicked(MouseEvent e) {
        // TODO add your code here
        genNotSelected = false;
    }
    private void radioButton3MouseClicked(MouseEvent e) {
        // TODO add your code here
        genNotSelected = false;
    }
    private void radioButton4MouseClicked(MouseEvent e) {
        // TODO add your code here
        genNotSelected = false;
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        String reportDate = null;
        try {
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
            if(currentDate.before(selectedDate)){
                JOptionPane.showMessageDialog(null,"Please choose a date before today!!");
                throw new Exception();
            }
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            reportDate = df.format(selectedDate);
            //JOptionPane.showMessageDialog(null, reportDate);
            dateNotSelected = false;
        }
        catch (Exception ex){
            dateNotSelected = true;
        }
        if(nameNotEntered || noNotEntered || userNotEntered || passNotEntered || emailNotEntered || genNotSelected || dateNotSelected
        || textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Fill all the details");

        }
        else{
            try {
                TripleDea td = new TripleDea();
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String name = textField1.getText();
                String phone = textField2.getText();
                String user = textField3.getText();
                String pass = String.valueOf(passwordField1.getPassword());
                String email = textField6.getText();
                String gen = null;
                if(radioButton1.isSelected()){
                    gen = "M";
                }
                else if(radioButton2.isSelected()){
                    gen = "F";
                }
                else if(radioButton3.isSelected()){
                    gen = "T";
                }
                else if(radioButton3.isSelected()){
                    gen = "O";
                }
                String insert_query = "insert into reader"
                        + " (name, phone, gender,username,password,dob,email)"
                        + " values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preSt = con.prepareStatement(insert_query);
                preSt.setString(1, name);
                preSt.setString(2, phone);
                preSt.setString(3, gen);
                preSt.setString(4, user);
                preSt.setString(5, td.encrypt(pass));
                preSt.setString(6, reportDate);
                preSt.setString(7, email);
                preSt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(null,"Thank you!!!!");
                new Login().setVisible(true);
                this.dispose();
            }
            catch (SQLException ee){
                if(isConstraintViolation(ee)) {
                    JOptionPane.showMessageDialog(null,"Username already exists");
                }
            }
            catch (ClassNotFoundException ee){
                System.out.print(ee);
            }
        }
    }
    public static boolean isConstraintViolation(SQLException e) {
        return e.getSQLState().startsWith("23");
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        radioButton4 = new JRadioButton();
        button1 = new JButton();
        label7 = new JLabel();
        progressBar1 = new JProgressBar();
        label8 = new JLabel();
        label9 = new JLabel();
        textField6 = new JTextField();
        passwordField1 = new JPasswordField();

        //======== this ========
        setTitle("SignUp");
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\signup1.png").getImage());
        setBackground(new Color(21, 21, 21));
        var contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Name:");
        label1.setForeground(new Color(204, 204, 255));

        //---- label2 ----
        label2.setText("Phone No.");
        label2.setForeground(new Color(204, 204, 255));

        //---- label3 ----
        label3.setText("Gender");
        label3.setForeground(new Color(204, 204, 255));

        //---- label4 ----
        label4.setText("Username");
        label4.setForeground(new Color(204, 204, 255));

        //---- label5 ----
        label5.setText("Password");
        label5.setForeground(new Color(204, 204, 255));

        //---- label6 ----
        label6.setText("DOB");
        label6.setForeground(new Color(204, 204, 255));

        //---- textField1 ----
        textField1.setForeground(new Color(204, 204, 255));
        textField1.setBackground(new Color(21, 21, 21));
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                textField1FocusLost(e);
            }
        });

        //---- textField2 ----
        textField2.setForeground(new Color(204, 204, 255));
        textField2.setBackground(new Color(21, 21, 21));
        textField2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                textField2FocusLost(e);
            }
        });

        //---- textField3 ----
        textField3.setForeground(new Color(204, 204, 255));
        textField3.setBackground(new Color(21, 21, 21));
        textField3.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                textField3FocusLost(e);
            }
        });

        //---- radioButton1 ----
        radioButton1.setText("M");
        radioButton1.setForeground(new Color(204, 204, 255));
        radioButton1.setBackground(new Color(32, 32, 32));
        radioButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                radioButton1MouseClicked(e);
            }
        });

        //---- radioButton2 ----
        radioButton2.setText("F");
        radioButton2.setForeground(new Color(204, 204, 255));
        radioButton2.setBackground(new Color(32, 32, 32));
        radioButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                radioButton2MouseClicked(e);
            }
        });

        //---- radioButton3 ----
        radioButton3.setText("T");
        radioButton3.setForeground(new Color(204, 204, 255));
        radioButton3.setBackground(new Color(32, 32, 32));
        radioButton3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                radioButton3MouseClicked(e);
            }
        });

        //---- radioButton4 ----
        radioButton4.setText("Others");
        radioButton4.setForeground(new Color(204, 204, 255));
        radioButton4.setBackground(new Color(32, 32, 32));
        radioButton4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                radioButton4MouseClicked(e);
            }
        });

        //---- button1 ----
        button1.setText("Signup");
        button1.setForeground(new Color(204, 204, 255));
        button1.setBackground(new Color(32, 32, 32));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });

        //---- label7 ----
        label7.setText("Sign Up");
        label7.setFont(label7.getFont().deriveFont(label7.getFont().getStyle() | Font.BOLD, label7.getFont().getSize() + 34f));
        label7.setForeground(new Color(204, 204, 255));

        //---- progressBar1 ----
        progressBar1.setForeground(Color.cyan);

        //---- label8 ----
        label8.setText("Password Strength");
        label8.setHorizontalAlignment(SwingConstants.CENTER);
        label8.setForeground(new Color(204, 204, 255));

        //---- label9 ----
        label9.setText("Email");
        label9.setForeground(new Color(204, 204, 255));

        //---- textField6 ----
        textField6.setForeground(new Color(204, 204, 255));
        textField6.setBackground(new Color(21, 21, 21));
        textField6.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                textField6FocusLost(e);
            }
        });

        //---- passwordField1 ----
        passwordField1.setForeground(new Color(204, 204, 255));
        passwordField1.setBackground(new Color(21, 21, 21));
        passwordField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                passwordField1FocusLost(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(radioButton1)
                            .addGap(21, 21, 21)
                            .addComponent(radioButton2)
                            .addGap(26, 26, 26)
                            .addComponent(radioButton3)
                            .addGap(26, 26, 26)
                            .addComponent(radioButton4))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(label8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label9, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                        .addComponent(label6, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(43, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(0, 202, Short.MAX_VALUE)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                    .addGap(166, 166, 166))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                    .addGap(5, 5, 5)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(radioButton1)
                                .addComponent(radioButton2)
                                .addComponent(radioButton3)
                                .addComponent(radioButton4))))
                    .addGap(5, 5, 5)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label8))
                    .addGap(5, 5, 5)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                        .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(5, 5, 5)
                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label9, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button1)
                    .addContainerGap(13, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        {
            ButtonGroup gb = new ButtonGroup();
            gb.add(radioButton1);
            gb.add(radioButton2);
            gb.add(radioButton3);
            gb.add(radioButton4);
        }
        {
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "month");
            p.put("text.year", "year");
            SqlDateModel model = new SqlDateModel();
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
            this.add(datePicker);
            datePicker.setBounds(130,270,195,20);
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton button1;
    private JLabel label7;
    private JProgressBar progressBar1;
    private JLabel label8;
    private JLabel label9;
    private JTextField textField6;
    private JPasswordField passwordField1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    /*public static void main(String[] args) {
        new SignUp().setVisible(true);
    }*/
}

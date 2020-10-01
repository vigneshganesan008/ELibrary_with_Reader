import java.awt.*;
import java.awt.event.*;
import java.nio.channels.ScatteringByteChannel;
import java.security.cert.CertPathBuilderSpi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
import java.sql.*;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * Created by JFormDesigner on Sat Apr 11 11:36:03 IST 2020
 */



/**
 * @author Vignesh
 */
public class Settings extends JFrame {
    private Login User;
    boolean passOk;
    boolean FpassOk;
    boolean CpassOk;
    Reader r;
    Color color = new Color(204,204,255);

    public Settings(Login User, Reader r) {
        this.User = User;
        this.r =r;
        initComponents();
    }

    private void tabbedPane3StateChanged(ChangeEvent e) {
        // TODO add your code here
        if (tabbedPane3.getSelectedIndex() == 1) {
            label3.setText(" Name: " + User.name);
            label4.setText(" Phone number: " + User.phone);
            label5.setText(" Username: " + User.username);
            label6.setText(" DOB: " + User.dob);
            label7.setText(" Gender: " + User.gender);
            label8.setText(" Email: " + User.email);
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                PreparedStatement cmd = con.prepareStatement(" select sum(duration) from log where username=? group by username");
                cmd.setString(1, User.username);
                ResultSet rs = cmd.executeQuery();
                if (rs.next()) {
                    label17.setText("No. of minutes spend reading books: "+rs.getInt(1));
                }
                con.close();
            }
            catch (Exception ee) {
                System.out.println(ee);
            }
        }
    }

    private void tabbedPane2StateChanged(ChangeEvent e) {
        // TODO add your code here
        if(tabbedPane2.getSelectedIndex() == 1){
            label3.setText(" Name: " + User.name);
            label4.setText(" Phone number: " + User.phone);
            label5.setText(" Username: " + User.username);
            label6.setText(" DOB: " + User.dob);
            label7.setText(" Gender: " + User.gender);
            label8.setText(" Email: " + User.email);
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                PreparedStatement cmd = con.prepareStatement(" select sum(duration) from log where username=? group by username");
                cmd.setString(1, User.username);
                ResultSet rs = cmd.executeQuery();
                if (rs.next()) {
                    label17.setText("No. of minutes spend reading books: "+rs.getInt(1));
                }
                con.close();
            }
            catch (Exception ee){
                System.out.println(ee);
            }
        }
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            Statement stmt = con.createStatement();
            String query = "update reader set username='"+textField2.getText()+"' where username='"+textField1.getText()+"'";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Username Updated");
            con.close();
            User.username = textField2.getText();
            r.User.username = textField2.getText();
            textField1.setText("");textField2.setText("");
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
    public static boolean isConstraintViolation(SQLException e) {
        return e.getSQLState().startsWith("23");
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
            passOk = true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Password length must be at least 8 ");
            passOk = false;
        }
    }

    private void passwordField2FocusLost(FocusEvent e) {
        // TODO add your code here
        if(passOk){
            if(String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword()))){
                FpassOk = true;
            }
            else{
                JOptionPane.showMessageDialog(null,"Password does not match");
                FpassOk = false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"New password must be atleast 8 chars long!");
            FpassOk = false;
        }
    }

    private void button2MouseClicked(MouseEvent e) {
        // TODO add your code here
        try {
            if(FpassOk && CpassOk && passOk) {
                TripleDea td = new TripleDea();
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
                Statement stmt = con.createStatement();
                String query = "update reader set password='" + td.encrypt(String.valueOf(passwordField2.getPassword())) + "' where username='" + User.username + "'";
                stmt.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Password Updated");
                con.close();
                User.password = String.valueOf(passwordField2.getPassword());
                r.User.password = String.valueOf(passwordField2.getPassword());
                passwordField1.setText("");passwordField2.setText("");textField4.setText("");progressBar1.setValue(0);
                passOk = false; CpassOk = false; FpassOk =false;
            }
            else {
                JOptionPane.showMessageDialog(null,"Check current password");
            }
        }
        catch (Exception ee){
            System.out.print(ee);
        }
    }

    private void textField4FocusLost(FocusEvent e) {
        // TODO add your code here
        try {
            TripleDea td = new TripleDea();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "123456789");
            PreparedStatement cmd = con.prepareStatement("select * from reader where username = ?");
            cmd.setString(1, User.username);
            ResultSet rs = cmd.executeQuery();
            if(rs.next()){
                if(textField4.getText().equals(td.decrypt(rs.getString("password")))){
                    CpassOk = true;
                }
                else{
                    CpassOk = false;
                }
            }
        }
        catch (Exception ee){
            System.out.println(ee);
        }
    }

    private void button3MouseClicked(MouseEvent e) {
        // TODO add your code here
        int size = (Integer)spinner1.getValue();
        String font = comboBox1.getItemAt(comboBox1.getSelectedIndex());
        Font f = null;
        if(toggleButton1.isSelected()&&toggleButton2.isSelected())
            f = new Font(font, Font.BOLD+Font.ITALIC, size);
        else if(toggleButton1.isSelected())
            f = new Font(font, Font.BOLD, size);
        else if(toggleButton2.isSelected())
            f = new Font(font, Font.ITALIC, size);
        else
            f = new Font(font, Font.PLAIN, size);
        r.textArea1.setFont(f);
        r.textArea1.setForeground(color);
    }

    private void button4MouseClicked(MouseEvent e) {
        // TODO add your code here
        int size = (Integer)spinner1.getValue();
        String font = comboBox1.getItemAt(comboBox1.getSelectedIndex());
        Font f = null;
        if(toggleButton1.isSelected()&&toggleButton2.isSelected())
            f = new Font(font, Font.BOLD+Font.ITALIC, size);
        else if(toggleButton1.isSelected())
            f = new Font(font, Font.BOLD, size);
        else if(toggleButton2.isSelected())
            f = new Font(font, Font.ITALIC, size);
        else
            f = new Font(font, Font.PLAIN, size);
        r.textArea1.setFont(f);
        r.textArea1.setForeground(color);
        this.dispose();
    }

    private void button5MouseClicked(MouseEvent e) {
        // TODO add your code here
        Color initialcolor=Color.RED;
        color=JColorChooser.showDialog(this,"Select a color",initialcolor);
        label16.setForeground(color);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vignesh
        tabbedPane2 = new JTabbedPane();
        panel6 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        spinner1 = new JSpinner();
        comboBox1 = new JComboBox<>();
        toggleButton1 = new JToggleButton();
        toggleButton2 = new JToggleButton();
        button3 = new JButton();
        button4 = new JButton();
        label15 = new JLabel();
        button5 = new JButton();
        label16 = new JLabel();
        panel2 = new JPanel();
        tabbedPane3 = new JTabbedPane();
        panel3 = new JPanel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label17 = new JLabel();
        panel4 = new JPanel();
        label9 = new JLabel();
        label10 = new JLabel();
        button1 = new JButton();
        textField1 = new JTextField();
        textField2 = new JTextField();
        panel5 = new JPanel();
        label11 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        button2 = new JButton();
        textField4 = new JTextField();
        progressBar1 = new JProgressBar();
        label14 = new JLabel();
        passwordField1 = new JPasswordField();
        passwordField2 = new JPasswordField();

        //======== this ========
        setIconImage(new ImageIcon("C:\\Users\\Vicky\\Documents\\Java\\EBook_Reader\\icons\\settings.png").getImage());
        setTitle("Settings");
        var contentPane = getContentPane();

        //======== tabbedPane2 ========
        {
            tabbedPane2.addChangeListener(e -> tabbedPane2StateChanged(e));

            //======== panel6 ========
            {
                panel6.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder (
                new javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e"
                , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
                , new java. awt .Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,12 )
                ,java . awt. Color .red ) ,panel6. getBorder () ) ); panel6. addPropertyChangeListener(
                new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
                ) { if( "\u0062or\u0064er" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
                ;} } );

                //---- label1 ----
                label1.setText("Size");

                //---- label2 ----
                label2.setText("Font");

                //---- spinner1 ----
                spinner1.setModel(new SpinnerNumberModel(1, 1, 100, 1));

                //---- comboBox1 ----
                comboBox1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "                                FONT",
                    "Abadi MT Condensed Light",
                    "Albertus Extra Bold",
                    "Albertus Medium",
                    "Antique Olive",
                    "Arial",
                    "Arial Black",
                    "Arial MT",
                    "Arial Narrow",
                    "Bazooka",
                    "Book Antiqua",
                    "Bookman Old Style",
                    "Boulder",
                    "Calisto MT",
                    "Calligrapher",
                    "Century Gothic",
                    "Century Schoolbook",
                    "Cezanne",
                    "CG Omega",
                    "CG Times",
                    "Charlesworth",
                    "Chaucer",
                    "Clarendon Condensed",
                    "Comic Sans MS",
                    "Copperplate Gothic Bold",
                    "Copperplate Gothic Light",
                    "Cornerstone",
                    "Coronet",
                    "Courier",
                    "Courier New",
                    "Cuckoo",
                    "Dauphin",
                    "Denmark",
                    "Fransiscan",
                    "Garamond",
                    "Geneva",
                    "Haettenschweiler",
                    "Heather",
                    "Helvetica",
                    "Herald",
                    "Impact",
                    "Jester",
                    "Letter Gothic",
                    "Lithograph",
                    "Lithograph Light",
                    "Long Island",
                    "Lucida Console",
                    "Lucida Handwriting",
                    "Lucida Sans",
                    "Lucida Sans Unicode",
                    "Marigold",
                    "Market",
                    "Matisse ITC",
                    "MS LineDraw",
                    "News GothicMT",
                    "OCR A Extended",
                    "Old Century",
                    "Pegasus",
                    "Pickwick",
                    "Poster",
                    "Pythagoras",
                    "Sceptre",
                    "Sherwood",
                    "Signboard",
                    "Socket",
                    "Steamer",
                    "Storybook",
                    "Subway",
                    "Tahoma",
                    "Technical",
                    "Teletype",
                    "Tempus Sans ITC",
                    "Times",
                    "Times New Roman",
                    "Times New Roman PS",
                    "Trebuchet MS",
                    "Tristan",
                    "Tubular",
                    "Unicorn",
                    "Univers",
                    "Univers Condensed",
                    "Vagabond",
                    "Verdana",
                    "Westminster ",
                    "Allegro",
                    "Amazone BT",
                    "AmerType Md BT",
                    "Arrus BT",
                    "Aurora Cn BT",
                    "AvantGarde Bk BT",
                    "AvantGarde Md BT",
                    "BankGothic Md BT",
                    "Benguiat Bk BT",
                    "BernhardFashion BT",
                    "BernhardMod BT",
                    "BinnerD",
                    "Bremen Bd BT",
                    "CaslonOpnface BT",
                    "Charter Bd BT",
                    "Charter BT",
                    "ChelthmITC Bk BT",
                    "CloisterBlack BT",
                    "CopperplGoth Bd BT",
                    "English 111 Vivace BT",
                    "EngraversGothic BT",
                    "Exotc350 Bd BT",
                    "Freefrm721 Blk BT",
                    "FrnkGothITC Bk BT",
                    "Futura Bk BT",
                    "Futura Lt BT",
                    "Futura Md BT",
                    "Futura ZBlk BT",
                    "FuturaBlack BT",
                    "Galliard BT",
                    "Geometr231 BT",
                    "Geometr231 Hv BT",
                    "Geometr231 Lt BT",
                    "GeoSlab 703 Lt BT",
                    "GeoSlab 703 XBd BT",
                    "GoudyHandtooled BT",
                    "GoudyOLSt BT",
                    "Humanst521 BT",
                    "Humanst 521 Cn BT",
                    "Humanst521 Lt BT",
                    "Incised901 Bd BT",
                    "Incised901 BT",
                    "Incised901 Lt BT",
                    "Informal011 BT",
                    "Kabel Bk BT",
                    "Kabel Ult BT",
                    "Kaufmann Bd BT",
                    "Kaufmann BT",
                    "Korinna BT",
                    "Lydian BT",
                    "Monotype Corsiva",
                    "NewsGoth BT",
                    "Onyx BT",
                    "OzHandicraft BT",
                    "PosterBodoni BT",
                    "PTBarnum BT",
                    "Ribbon131 Bd BT",
                    "Serifa BT",
                    "Serifa Th BT",
                    "ShelleyVolante BT",
                    "Souvenir Lt BT",
                    "Staccato222 BT",
                    "Swis721 BlkEx BT",
                    "Swiss911 XCm BT",
                    "TypoUpright BT",
                    "ZapfEllipt BT",
                    "ZapfHumnst BT",
                    "ZapfHumnst Dm BT",
                    "Zurich BlkEx BT",
                    "Zurich Ex BT "
                }));

                //---- toggleButton1 ----
                toggleButton1.setText("Bold");

                //---- toggleButton2 ----
                toggleButton2.setText("Italics");

                //---- button3 ----
                button3.setText("Apply");
                button3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button3MouseClicked(e);
                    }
                });

                //---- button4 ----
                button4.setText("OK");
                button4.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button4MouseClicked(e);
                    }
                });

                //---- label15 ----
                label15.setText("Style");

                //---- button5 ----
                button5.setText("Color");
                button5.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button5MouseClicked(e);
                    }
                });

                //---- label16 ----
                label16.setText("Color Sample");

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addGroup(panel6Layout.createParallelGroup()
                                .addGroup(panel6Layout.createSequentialGroup()
                                    .addGap(36, 36, 36)
                                    .addGroup(panel6Layout.createParallelGroup()
                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label2)
                                        .addComponent(label15))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel6Layout.createParallelGroup()
                                        .addGroup(panel6Layout.createSequentialGroup()
                                            .addGap(26, 26, 26)
                                            .addComponent(button3)
                                            .addGap(44, 44, 44)
                                            .addComponent(button4))
                                        .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                                .addComponent(toggleButton1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(toggleButton2))
                                            .addComponent(spinner1, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                            .addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))))
                                .addGroup(panel6Layout.createSequentialGroup()
                                    .addGap(170, 170, 170)
                                    .addComponent(button5)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label16)))
                            .addContainerGap(98, Short.MAX_VALUE))
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(spinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(29, 29, 29)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(toggleButton2)
                                .addComponent(toggleButton1)
                                .addComponent(label15))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button5)
                                .addComponent(label16))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button4)
                                .addComponent(button3))
                            .addGap(25, 25, 25))
                );
            }
            tabbedPane2.addTab("Reader Settings", panel6);

            //======== panel2 ========
            {

                //======== tabbedPane3 ========
                {
                    tabbedPane3.addChangeListener(e -> tabbedPane3StateChanged(e));

                    //======== panel3 ========
                    {

                        //---- label3 ----
                        label3.setText("text");

                        //---- label4 ----
                        label4.setText("text");

                        //---- label5 ----
                        label5.setText("text");

                        //---- label6 ----
                        label6.setText("text");

                        //---- label7 ----
                        label7.setText("text");

                        //---- label8 ----
                        label8.setText("text");

                        //---- label17 ----
                        label17.setText("text");

                        GroupLayout panel3Layout = new GroupLayout(panel3);
                        panel3.setLayout(panel3Layout);
                        panel3Layout.setHorizontalGroup(
                            panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGap(54, 54, 54)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label7, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label8, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label17, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(81, Short.MAX_VALUE))
                        );
                        panel3Layout.setVerticalGroup(
                            panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label8, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label17, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(36, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane3.addTab("Info", panel3);

                    //======== panel4 ========
                    {

                        //---- label9 ----
                        label9.setText("Current Username");

                        //---- label10 ----
                        label10.setText("New Username");

                        //---- button1 ----
                        button1.setText("Change Username");
                        button1.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                button1MouseClicked(e);
                            }
                        });

                        GroupLayout panel4Layout = new GroupLayout(panel4);
                        panel4.setLayout(panel4Layout);
                        panel4Layout.setHorizontalGroup(
                            panel4Layout.createParallelGroup()
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addGroup(panel4Layout.createParallelGroup()
                                        .addGroup(panel4Layout.createSequentialGroup()
                                            .addGap(50, 50, 50)
                                            .addGroup(panel4Layout.createParallelGroup()
                                                .addComponent(label10)
                                                .addComponent(label9))
                                            .addGap(49, 49, 49)
                                            .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                                .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                                        .addGroup(panel4Layout.createSequentialGroup()
                                            .addGap(163, 163, 163)
                                            .addComponent(button1)))
                                    .addContainerGap(64, Short.MAX_VALUE))
                        );
                        panel4Layout.setVerticalGroup(
                            panel4Layout.createParallelGroup()
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label9)
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(25, 25, 25)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label10)
                                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                                    .addComponent(button1)
                                    .addContainerGap())
                        );
                    }
                    tabbedPane3.addTab("Change Username", panel4);

                    //======== panel5 ========
                    {

                        //---- label11 ----
                        label11.setText("Current Password");

                        //---- label12 ----
                        label12.setText("New Password");

                        //---- label13 ----
                        label13.setText("Re-enter new password");

                        //---- button2 ----
                        button2.setText("Change Password");
                        button2.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                button2MouseClicked(e);
                            }
                        });

                        //---- textField4 ----
                        textField4.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                textField4FocusLost(e);
                            }
                        });

                        //---- label14 ----
                        label14.setText("Password Strength");

                        //---- passwordField1 ----
                        passwordField1.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                passwordField1FocusLost(e);
                            }
                        });

                        //---- passwordField2 ----
                        passwordField2.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                passwordField2FocusLost(e);
                            }
                        });

                        GroupLayout panel5Layout = new GroupLayout(panel5);
                        panel5.setLayout(panel5Layout);
                        panel5Layout.setHorizontalGroup(
                            panel5Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                                    .addContainerGap(350, Short.MAX_VALUE)
                                    .addComponent(label14)
                                    .addGap(32, 32, 32))
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGroup(panel5Layout.createParallelGroup()
                                                .addGroup(panel5Layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(label11)
                                                    .addGap(30, 30, 30))
                                                .addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(panel5Layout.createParallelGroup()
                                                        .addComponent(label12)
                                                        .addComponent(label13))))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(textField4, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                                    .addComponent(passwordField1, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                                                .addComponent(passwordField2, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(169, 169, 169)
                                            .addComponent(button2)))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel5Layout.setVerticalGroup(
                            panel5Layout.createParallelGroup()
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(27, 27, 27)
                                            .addComponent(label11))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                    .addGap(3, 3, 3)
                                    .addComponent(label14)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label12)
                                                .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(27, 27, 27)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label13)
                                                .addComponent(passwordField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                                            .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                            .addGap(57, 57, 57)))
                                    .addGap(30, 30, 30)
                                    .addComponent(button2)
                                    .addContainerGap())
                        );
                    }
                    tabbedPane3.addTab("Change password", panel5);
                }

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(tabbedPane3, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                            .addContainerGap())
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addComponent(tabbedPane3)
                            .addContainerGap())
                );
            }
            tabbedPane2.addTab("Profile Settings", panel2);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane2)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane2)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vignesh
    private JTabbedPane tabbedPane2;
    private JPanel panel6;
    private JLabel label1;
    private JLabel label2;
    private JSpinner spinner1;
    private JComboBox<String> comboBox1;
    private JToggleButton toggleButton1;
    private JToggleButton toggleButton2;
    private JButton button3;
    private JButton button4;
    private JLabel label15;
    private JButton button5;
    private JLabel label16;
    private JPanel panel2;
    private JTabbedPane tabbedPane3;
    private JPanel panel3;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label17;
    private JPanel panel4;
    private JLabel label9;
    private JLabel label10;
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel panel5;
    private JLabel label11;
    private JLabel label12;
    private JLabel label13;
    private JButton button2;
    private JTextField textField4;
    private JProgressBar progressBar1;
    private JLabel label14;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}

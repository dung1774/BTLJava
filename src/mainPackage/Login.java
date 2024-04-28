package mainPackage;

import dao.ConnectionProvider;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField txtEmail = new JTextField();
    JLabel jLabel3 = new JLabel();
    JTextField txtPassword = new JPasswordField();
    JButton btnLogIn = new JButton();
    JButton btnClose = new JButton();
    JLabel jLabel4 = new JLabel();
    JButton btnSignUp = new JButton();
    JLabel jLabel5 = new JLabel();

    public Login() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        setLayout(null);
        setSize(1368, 768);

        jLabel1.setFont(new Font("Segoe UI", 1, 24));
        jLabel1.setText("Welcome");
        add(jLabel1);
        jLabel1.setBounds(1090, 220, 104, 32);

        jLabel2.setFont(new Font("Segoe UI", 1, 14));
        jLabel2.setText("Email");
        add(jLabel2);
        jLabel2.setBounds(980, 290, 50, 20);

        txtEmail.setFont(new Font("Segoe UI", 1, 14));
        add(txtEmail);
        txtEmail.setBounds(980, 320, 380, 30);

        jLabel3.setFont(new Font("Segoe UI", 1, 14));
        jLabel3.setText("Password");
        add(jLabel3);
        jLabel3.setBounds(980, 360, 64, 20);

        txtPassword.setFont(new Font("Segoe UI", 1, 14));
        add(txtPassword);
        txtPassword.setBounds(980, 390, 380, 30);

        btnLogIn.setFont(new Font("Segoe UI", 1, 14));
        btnLogIn.setIcon(new ImageIcon(getClass().getResource("/images/login.png")));
        btnLogIn.setText("Login");
        add(btnLogIn);
        btnLogIn.setBounds(1110, 450, 140, 30);
        btnLogIn.addActionListener(this);

        btnClose.setFont(new Font("Segoe UI", 1, 14));
        btnClose.setIcon(new ImageIcon(getClass().getResource("/images/close.png")));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(1110, 500, 140, 30);
        btnClose.addActionListener(this);

        jLabel4.setFont(new Font("Tahoma", 1, 14));
        jLabel4.setText("If you dont have an account, press here!");
        add(jLabel4);
        jLabel4.setBounds(1030, 560, 300, 17);

        btnSignUp.setFont(new Font("Tahoma", 1, 14));
        btnSignUp.setText("Sign Up");
        add(btnSignUp);
        btnSignUp.setBounds(1130, 600, 90, 30);
        btnSignUp.addActionListener(this);

        jLabel5.setIcon(new ImageIcon(getClass().getResource("/images/login-background.PNG")));
        add(jLabel5);
        jLabel5.setBounds(0, 0, 1368, 768);
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        new Login().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnLogIn)) {
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            int temp = 0;
            try {
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select *from admin where email='" + email + "' and password='" + password + "'");
                while (rs.next()) {
                    temp = 1;
                    String adminName = rs.getString("name");
                    setVisible(false);
                    new HomeAdmin(adminName).setVisible(true);
                }
                rs = st.executeQuery("SELECT * FROM customer WHERE email='" + email + "' AND password='" + password + "'");
                while (rs.next()) {
                    temp = 1;
                    String CustomerPk = rs.getString("customer_pk");
                    setVisible(false);
                    new HomeCustomer(CustomerPk).setVisible(true);
                }
                con.close();
                if (temp == 0) {
                    JOptionPane.showMessageDialog(null, "Incorrect Email or Password");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if (d.getSource().equals(btnSignUp)) {
            new SignUp().setVisible(true);
        }

        if (d.getSource().equals(btnClose)) {
            int a = JOptionPane.showConfirmDialog(null, "Do you want to close application?", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                System.exit(0);
            }
        }
    }
}

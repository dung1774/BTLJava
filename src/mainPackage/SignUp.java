package mainPackage;

import dao.ConnectionProvider;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends JFrame implements ActionListener {

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JTextField txtUserName = new JTextField();
    JTextField txtEmail = new JTextField();
    JTextField txtPassword = new JTextField();
    JTextField txtMobileNumber = new JTextField();
    JTextField txtAddress = new JTextField();
    JButton btnSignUp = new JButton();
    JButton btnClose = new JButton();
    JLabel jLabel8 = new JLabel();

    public SignUp() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        setLayout(null);
        setSize(500, 350);

        jLabel1.setFont(new Font("Tahoma", 1, 18));
        jLabel1.setText("SIGN UP");
        add(jLabel1);
        jLabel1.setBounds(205, 24, 90, 25);

        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jLabel2.setText("UserName");
        add(jLabel2);
        jLabel2.setBounds(40, 77, 77, 15);

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setText("Email");
        add(jLabel3);
        jLabel3.setBounds(40, 117, 52, 15);

        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setText("Password");
        add(jLabel4);
        jLabel4.setBounds(40, 160, 77, 15);

        jLabel5.setFont(new Font("Tahoma", 1, 12));
        jLabel5.setText("MobileNumer");
        add(jLabel5);
        jLabel5.setBounds(40, 204, 91, 15);

        jLabel6.setFont(new Font("Tahoma", 1, 12));
        jLabel6.setText("Address");
        add(jLabel6);
        jLabel6.setBounds(40, 248, 66, 15);

        add(txtUserName);
        txtUserName.setBounds(143, 73, 242, 28);

        add(txtEmail);
        txtEmail.setBounds(143, 113, 242, 28);

        add(txtPassword);
        txtPassword.setBounds(143, 156, 242, 28);

        add(txtMobileNumber);
        txtMobileNumber.setBounds(143, 200, 242, 28);

        add(txtAddress);
        txtAddress.setBounds(143, 244, 242, 28);

        btnSignUp.setFont(new Font("Tahoma", 1, 12));
        btnSignUp.setText("Sign Up");
        add(btnSignUp);
        btnSignUp.setBounds(143, 287, 76, 28);
        btnSignUp.addActionListener(this);

        btnClose.setFont(new Font("Tahoma", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(313, 287, 72, 28);
        btnClose.addActionListener(this);

        jLabel8.setIcon(new ImageIcon(getClass().getResource("/images/All_page_Background.png")));
        add(jLabel8);
        jLabel8.setBounds(0, 0, 500, 350);
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
        new SignUp().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnSignUp)) {
            String name = txtUserName.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String mobileNumber = txtMobileNumber.getText();
            String address = txtAddress.getText();
            if (email.isEmpty() || password.isEmpty() || address.isEmpty() || address.isEmpty() || mobileNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }
            try {
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM customer WHERE email='" + email + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Account already exists. Please sign in.");
                } else {
                    PreparedStatement ps = con.prepareStatement("insert into customer (name,password,mobileNumber,email,address) values (?,?,?,?,?)");
                    ps.setString(1, name);
                    ps.setString(3, mobileNumber);
                    ps.setString(4, email);
                    ps.setString(2, password);
                    ps.setString(5, address);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Account created successfully.");
                }
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if (d.getSource().equals(btnClose)) {
            int a = JOptionPane.showConfirmDialog(null, "Do you want to close sign up?", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                setVisible(false);
                new Login().setVisible(true);
            } else {
                return;
            }
        }
    }
}

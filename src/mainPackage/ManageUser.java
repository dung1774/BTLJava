package mainPackage;

import dao.ConnectionProvider;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ManageUser extends JFrame implements ActionListener, MouseListener {

    private int adminPk = 0;

    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable tableUser = new JTable();
    JLabel jLabel2 = new JLabel();
    JTextField txtName = new JTextField();
    JLabel jLabel3 = new JLabel();
    JTextField txtMobileNumber = new JTextField();
    JLabel jLabel4 = new JLabel();
    JTextField txtEmail = new JTextField();
    JLabel jLabel5 = new JLabel();
    JTextField txtAddress = new JTextField();
    JLabel jLabel7 = new JLabel();
    JTextField txtPassword = new JTextField();
    JButton btnSave = new JButton();
    JButton btnUpdate = new JButton();
    JButton btnReset = new JButton();
    JButton btnClose = new JButton();
    JLabel jLabel8 = new JLabel();

    public ManageUser() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private boolean validateFields(String formType) {
        if (formType.equals("edit") && !txtName.getText().equals("") && !txtMobileNumber.getText().equals("") && !txtEmail.getText().equals("") && !txtAddress.getText().equals("")) {
            return false;
        } else if (formType.equals("new") && !txtName.getText().equals("") && !txtMobileNumber.getText().equals("") && !txtEmail.getText().equals("") && !txtAddress.getText().equals("") && !txtPassword.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void initComponents() {

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        setLayout(null);
        setSize(850, 600);

        jLabel1.setFont(new Font("Impact", 1, 36));
        jLabel1.setText("Manage User");
        add(jLabel1);
        jLabel1.setBounds(307, 0, 270, 45);

        tableUser.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Mobile Number", "Email", "Address"
                }
        ));
        jScrollPane1.setViewportView(tableUser);
        tableUser.addMouseListener(this);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 60, 421, 506);

        jLabel2.setFont(new Font("Segoe UI", 1, 12));
        jLabel2.setText("Name");
        add(jLabel2);
        jLabel2.setBounds(470, 60, 37, 16);

        txtName.setFont(new Font("Segoe UI", 1, 12));
        add(txtName);
        txtName.setBounds(470, 90, 340, 27);

        jLabel3.setFont(new Font("Segoe UI", 1, 12));
        jLabel3.setText("Mobile Number");
        add(jLabel3);
        jLabel3.setBounds(470, 130, 100, 16);

        txtMobileNumber.setFont(new Font("Segoe UI", 1, 12));
        add(txtMobileNumber);
        txtMobileNumber.setBounds(470, 170, 340, 27);

        jLabel4.setFont(new Font("Segoe UI", 1, 12));
        jLabel4.setText("Email");
        add(jLabel4);
        jLabel4.setBounds(470, 210, 37, 16);

        txtEmail.setFont(new Font("Segoe UI", 1, 12));
        add(txtEmail);
        txtEmail.setBounds(470, 240, 340, 27);

        jLabel5.setFont(new Font("Segoe UI", 1, 12));
        jLabel5.setText("Address");
        add(jLabel5);
        jLabel5.setBounds(470, 280, 60, 16);

        txtAddress.setFont(new Font("Segoe UI", 1, 12));
        add(txtAddress);
        txtAddress.setBounds(470, 320, 340, 27);

        jLabel7.setFont(new Font("Segoe UI", 1, 12));
        jLabel7.setText("Password");
        add(jLabel7);
        jLabel7.setBounds(470, 360, 70, 16);

        txtPassword.setFont(new Font("Segoe UI", 1, 12));
        add(txtPassword);
        txtPassword.setBounds(470, 390, 340, 27);

        btnSave.setFont(new Font("Segoe UI", 1, 12));
        btnSave.setText("Save");
        add(btnSave);
        btnSave.setBounds(470, 500, 72, 27);
        btnSave.addActionListener(this);

        btnUpdate.setFont(new Font("Segoe UI", 1, 12));
        btnUpdate.setText("Update");
        add(btnUpdate);
        btnUpdate.setBounds(560, 500, 72, 27);
        btnUpdate.addActionListener(this);

        btnReset.setFont(new Font("Segoe UI", 1, 12));
        btnReset.setText("Reset");
        add(btnReset);
        btnReset.setBounds(650, 500, 72, 27);
        btnReset.addActionListener(this);

        btnClose.setFont(new Font("Segoe UI", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(740, 500, 72, 27);
        btnClose.addActionListener(this);

        jLabel8.setIcon(new ImageIcon(getClass().getResource("/images/All_page_Background.png")));
        jLabel8.setText("jLabel8");
        add(jLabel8);
        jLabel8.setBounds(0, 0, 850, 600);
    }

    private void formComponentShown(ComponentEvent evt) {
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableUser.getModel();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from admin");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("admin_pk"), rs.getString("name"), rs.getString("mobileNumber"), rs.getString("email"), rs.getString("address")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        btnUpdate.setEnabled(false);
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
        new ManageUser().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnSave)) {
            String name = txtName.getText();
            String mobileNumber = txtMobileNumber.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String address = txtAddress.getText();

            if (validateFields("new")) {
                JOptionPane.showMessageDialog(null, "All field are required");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("insert into admin (name,mobileNumber,email,password,address) values (?,?,?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, mobileNumber);
                    ps.setString(3, email);
                    ps.setString(4, password);
                    ps.setString(5, address);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "admin Added Successfully");
                    setVisible(false);
                    new ManageUser().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnUpdate)) {
            String name = txtName.getText();
            String mobileNumber = txtMobileNumber.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();

            if (validateFields("edit")) {
                JOptionPane.showMessageDialog(null, "All field are required");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("update admin set name=?,mobileNumber=?,email=?,address=? where admin_pk=?");
                    ps.setString(1, name);
                    ps.setString(2, mobileNumber);
                    ps.setString(3, email);
                    ps.setString(4, address);
                    ps.setInt(6, adminPk);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "admin Updated Successfully");
                    setVisible(false);
                    new ManageUser().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnReset)) {
            setVisible(false);
            new ManageUser().setVisible(true);
        }

        if (d.getSource().equals(btnClose)) {
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent d) {
        if (d.getSource().equals(tableUser)) {
            int index = tableUser.getSelectedRow();
            TableModel model = tableUser.getModel();

            String id = model.getValueAt(index, 0).toString();
            adminPk = Integer.parseInt(id);

            String name = model.getValueAt(index, 1).toString();
            txtName.setText(name);

            String mobileNumber = model.getValueAt(index, 2).toString();
            txtMobileNumber.setText(mobileNumber);

            String email = model.getValueAt(index, 3).toString();
            txtEmail.setText(email);

            String address = model.getValueAt(index, 4).toString();
            txtAddress.setText(address);

            txtPassword.setEditable(false);
            txtPassword.setBackground(Color.DARK_GRAY);

            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

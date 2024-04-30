package mainPackage;

import dao.ConnectionProvider;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ManageCustomer extends JFrame implements ActionListener, MouseListener {

    private int appuserPk = 0;

    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable tableCustomer = new JTable();
    JLabel jLabel2 = new JLabel();
    JTextField txtName = new JTextField();
    JLabel jLabel3 = new JLabel();
    JTextField txtMobileNumber = new JTextField();
    JLabel jLabel4 = new JLabel();
    JTextField txtEmail = new JTextField();
    JButton btnSave = new JButton();
    JButton btnUpdate = new JButton();
    JButton btnReset = new JButton();
    JButton btnClose = new JButton();
    JLabel jLabel5 = new JLabel();

    public ManageCustomer() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private boolean validateFields() {
        if (!txtName.getText().equals("") && !txtMobileNumber.getText().equals("") && !txtEmail.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        setLayout(null);
        setSize(850, 600);

        jLabel1.setFont(new Font("Impact", 1, 36));
        jLabel1.setText("Manage Customer");
        add(jLabel1);
        jLabel1.setBounds(265, 24, 285, 45);

        tableCustomer.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Mobile Number", "Email"
                }
        ));
        jScrollPane1.setViewportView(tableCustomer);
        tableCustomer.addMouseListener(this);

        add(jScrollPane1);
        jScrollPane1.setBounds(17, 123, 375, 402);

        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jLabel2.setText("Name");
        add(jLabel2);
        jLabel2.setBounds(471, 164, 40, 15);

        txtName.setFont(new Font("Tahoma", 1, 12));
        add(txtName);
        txtName.setBounds(471, 197, 370, 27);

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setText("Mobile Number");
        add(jLabel3);
        jLabel3.setBounds(471, 236, 90, 15);

        txtMobileNumber.setFont(new Font("Tahoma", 1, 12));
        add(txtMobileNumber);
        txtMobileNumber.setBounds(471, 269, 370, 27);

        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setText("Email");
        add(jLabel4);
        jLabel4.setBounds(471, 308, 46, 15);

        txtEmail.setFont(new Font("Tahoma", 1, 12));
        add(txtEmail);
        txtEmail.setBounds(471, 341, 370, 27);

        btnSave.setFont(new Font("Tahoma", 1, 12));
        btnSave.setText("Save");
        add(btnSave);
        btnSave.setBounds(491, 396, 60, 27);
        btnSave.addActionListener(this);

        btnUpdate.setFont(new Font("Tahoma", 1, 12));
        btnUpdate.setText("Update");
        add(btnUpdate);
        btnUpdate.setBounds(571, 396, 72, 27);
        btnUpdate.addActionListener(this);

        btnReset.setFont(new Font("Tahoma", 1, 12));
        btnReset.setText("Reset");
        add(btnReset);
        btnReset.setBounds(664, 396, 70, 27);
        btnReset.addActionListener(this);

        btnClose.setFont(new Font("Tahoma", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(754, 396, 70, 27);
        btnClose.addActionListener(this);

        jLabel5.setIcon(new ImageIcon(getClass().getResource("/images/All_page_Background.png")));
        add(jLabel5);
        jLabel5.setBounds(0, 0, 850, 600);
    }

    private void formComponentShown(ComponentEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        try {
            Connection connection = ConnectionProvider.getCon();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select *from customer");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("customer_pk"), rs.getString("name"), rs.getString("mobileNumber"), rs.getString("email")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        btnUpdate.setEnabled(false);
        // TODO add your handling code here:
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnSave)) {
            String name = txtName.getText();
            String mobileNumber = txtMobileNumber.getText();
            String email = txtEmail.getText();

            if (validateFields()) {
                JOptionPane.showMessageDialog(null, "Tất cả các trường không được để trống!");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("insert into customer (name,mobileNumber,email) values (?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, mobileNumber);
                    ps.setString(3, email);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
                    setVisible(false);
                    new ManageCustomer().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnReset)) {
            setVisible(false);
            new ManageCustomer().setVisible(true);
        }

        if (d.getSource().equals(btnUpdate)) {
            String name = txtName.getText();
            String mobileNumber = txtMobileNumber.getText();
            String email = txtEmail.getText();

            if (validateFields()) {
                JOptionPane.showMessageDialog(null, "Tất cả các trường không được để trống!");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("Update customer set name=?,mobileNumber=?,email=? where customer_pk=?");
                    ps.setString(1, name);
                    ps.setString(2, mobileNumber);
                    ps.setString(3, email);
                    ps.setInt(4, appuserPk);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");
                    setVisible(false);
                    new ManageCustomer().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnClose)) {
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent d) {
        if (d.getSource().equals(tableCustomer)) {
            int index = tableCustomer.getSelectedRow();
            TableModel model = tableCustomer.getModel();
            String id = model.getValueAt(index, 0).toString();
            appuserPk = Integer.parseInt(id);

            String name = model.getValueAt(index, 1).toString();
            txtName.setText(name);

            String mobileNumber = model.getValueAt(index, 2).toString();
            txtMobileNumber.setText(mobileNumber);

            String email = model.getValueAt(index, 3).toString();
            txtEmail.setText(email);

            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
        }
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
        new ManageCustomer().setVisible(true);
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

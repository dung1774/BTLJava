package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeAdmin extends JFrame implements ActionListener {

    JButton btnUser = new JButton();
    JButton btnCategory = new JButton();
    JButton btnProduct = new JButton();
    JButton btnCustomer = new JButton();
    JButton btnOrder = new JButton();
    JButton btnView = new JButton();
    JButton btnLogOut = new JButton();
    JLabel jLabel1 = new JLabel();
    
    public HomeAdmin() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public HomeAdmin(String adminName) {
        initComponents();
        setLocationRelativeTo(null);
        if (adminName.equals("Super Admin")) {
            btnUser.setVisible(true);
        } else {
            btnUser.setVisible(false);
        }
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        
        setLayout(null);
        setSize(1368, 768);

        btnUser.setFont(new Font("Segoe UI", 1, 18));
        btnUser.setIcon(new ImageIcon(getClass().getResource("/images/Users.png")));
        btnUser.setText("User");
        add(btnUser);
        btnUser.setBounds(110, 70, 130, 50);
        btnUser.addActionListener(this);

        btnCategory.setFont(new Font("Segoe UI", 1, 18));
        btnCategory.setIcon(new ImageIcon(getClass().getResource("/images/category.png")));
        btnCategory.setText("Category");
        add(btnCategory);
        btnCategory.setBounds(250, 70, 160, 50);
        btnCategory.addActionListener(this);

        btnProduct.setFont(new Font("Segoe UI", 1, 18));
        btnProduct.setIcon(new ImageIcon(getClass().getResource("/images/product.png")));
        btnProduct.setText("Product");
        add(btnProduct);
        btnProduct.setBounds(420, 70, 160, 50);
        btnProduct.addActionListener(this);

        btnCustomer.setFont(new Font("Segoe UI", 1, 18));
        btnCustomer.setIcon(new ImageIcon(getClass().getResource("/images/customers.png")));
        btnCustomer.setText("Customer");
        add(btnCustomer);
        btnCustomer.setBounds(590, 70, 160, 50);
        btnCustomer.addActionListener(this);

        btnOrder.setFont(new Font("Segoe UI", 1, 18));
        btnOrder.setIcon(new ImageIcon(getClass().getResource("/images/Orders.png")));
        btnOrder.setText(" Order");
        add(btnOrder);
        btnOrder.setBounds(760, 70, 128, 50);
        btnOrder.addActionListener(this);

        btnView.setFont(new Font("Segoe UI", 1, 18));
        btnView.setIcon(new ImageIcon(getClass().getResource("/images/View-orders.png")));
        btnView.setText("View Order");
        add(btnView);
        btnView.setBounds(900, 70, 169, 50);
        btnView.addActionListener(this);

        btnLogOut.setFont(new Font("Segoe UI", 1, 18));
        btnLogOut.setIcon(new ImageIcon(getClass().getResource("/images/Exit.png")));
        btnLogOut.setText("Logout");
        add(btnLogOut);
        btnLogOut.setBounds(1084, 70, 140, 50);
        btnLogOut.addActionListener(this);

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/images/Background1.png")));
        jLabel1.setText("jLabel1");
        add(jLabel1);
        jLabel1.setBounds(0, 0, 1370, 768);
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
        new HomeAdmin().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnUser)) {
            new ManageUser().setVisible(true);
        }

        if (d.getSource().equals(btnCategory)) {
            new ManageCategory().setVisible(true);
        }

        if (d.getSource().equals(btnCustomer)) {
            new ManageCustomer().setVisible(true);
        }

        if (d.getSource().equals(btnProduct)) {
            new ManageProduct().setVisible(true);
        }

        if (d.getSource().equals(btnOrder)) {
            new ManageOrder().setVisible(true);
        }

        if (d.getSource().equals(btnView)) {
            new ViewOrders().setVisible(true);
        }

        if (d.getSource().equals(btnLogOut)) {
            int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát khỏi ứng dụng?", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                setVisible(false);
                new Login().setVisible(true);
            }
        }
    }
}

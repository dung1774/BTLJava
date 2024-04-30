package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeCustomer extends JFrame implements ActionListener {

    String CustomerPk = "";

    JButton btnOrder = new JButton();
    JButton btnView = new JButton();
    JButton btnLogOut = new JButton();
    JLabel jLabel1 = new JLabel();

    public HomeCustomer(String customerPk) {
        initComponents();
        setLocationRelativeTo(null);
        this.CustomerPk = customerPk;
    }

    public HomeCustomer() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        setLayout(null);
        setSize(1020, 620);

        btnOrder.setFont(new Font("Segoe UI", 1, 18));
        btnOrder.setIcon(new ImageIcon(getClass().getResource("/images/Orders.png")));
        btnOrder.setText(" Order");
        add(btnOrder);
        btnOrder.setBounds(160, 80, 140, 50);
        btnOrder.addActionListener(this);

        btnView.setFont(new Font("Segoe UI", 1, 18));
        btnView.setIcon(new ImageIcon(getClass().getResource("/images/View-orders.png")));
        btnView.setText("View Order");
        add(btnView);
        btnView.setBounds(410, 80, 180, 50);
        btnView.addActionListener(this);

        btnLogOut.setFont(new Font("Segoe UI", 1, 18));
        btnLogOut.setIcon(new ImageIcon(getClass().getResource("/images/Exit.png")));
        btnLogOut.setText("Logout");
        add(btnLogOut);
        btnLogOut.setBounds(690, 80, 150, 50);
        btnLogOut.addActionListener(this);

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/images/Background3.png")));
        jLabel1.setText("jLabel1");
        add(jLabel1);
        jLabel1.setBounds(0, 0, 1020, 620);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnOrder)) {
            new CustomerOrder(CustomerPk).setVisible(true);
        }

        if (d.getSource().equals(btnView)) {
            new ViewCustomerOrders(CustomerPk).setVisible(true);
        }

        if (d.getSource().equals(btnLogOut)) {
            int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát khỏi ứng dụng?", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                setVisible(false);
                new Login().setVisible(true);
            }
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
        new HomeCustomer().setVisible(true);
    }
}

package mainPackage;

import common.OpenPdf;
import dao.ConnectionProvider;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ViewOrders extends JFrame implements ActionListener, MouseListener {

    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable tableCustomer = new JTable();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTable tableOrders = new JTable();
    JButton btnClose = new JButton();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();

    public ViewOrders() {
        initComponents();
        setLocationRelativeTo(null);
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
        jLabel1.setText("View Orders");
        add(jLabel1);
        jLabel1.setBounds(327, 6, 187, 45);

        tableCustomer.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Mobile Number", "Email"
                }
        ));
        jScrollPane1.setViewportView(tableCustomer);
        tableCustomer.addMouseListener(this);

        add(jScrollPane1);
        jScrollPane1.setBounds(6, 101, 412, 402);

        tableOrders.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Order ID", "Date", "Total Paid"
                }
        ));
        jScrollPane2.setViewportView(tableOrders);
        tableOrders.addMouseListener(this);

        add(jScrollPane2);
        jScrollPane2.setBounds(436, 101, 408, 402);

        btnClose.setFont(new Font("Tahoma", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(770, 550, 72, 27);
        btnClose.addActionListener(this);

        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jLabel2.setText("Customer List");
        add(jLabel2);
        jLabel2.setBounds(167, 74, 82, 15);

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setText("Order List");
        add(jLabel3);
        jLabel3.setBounds(612, 74, 70, 15);

        jLabel4.setIcon(new ImageIcon(getClass().getResource("/images/All_page_Background.png")));
        add(jLabel4);
        jLabel4.setBounds(0, 0, 850, 600);
    }

    private void formComponentShown(ComponentEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from customer");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("customer_pk"), rs.getString("name"),
                    rs.getString("mobileNumber"), rs.getString("email")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent d) {
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

            DefaultTableModel orderModel = (DefaultTableModel) tableOrders.getModel();
            orderModel.setRowCount(0);

            try {
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select *from orderDetail where customer_fk=" + id + "");
                while (rs.next()) {
                    orderModel.addRow(new Object[]{rs.getString("orderId"),
                        rs.getString("orderDate"), rs.getString("totalPaid")});
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if (d.getSource().equals(tableOrders)) {
            int index = tableOrders.getSelectedRow();
            TableModel model = tableOrders.getModel();
            String orderId = model.getValueAt(index, 0).toString();
            OpenPdf.OpenByIdForAdmin(orderId);
            if (OpenPdf.checkBill == true) {
                try {
                    Connection con = ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    int rowsAffected = st.executeUpdate("delete from orderDetail where orderId='" + orderId + "'");
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Xoá hoá đơn thành công!");
                        setVisible(false);
                        new ViewOrders().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy hoá đơn tương ứng để xoá!");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
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
        new ViewOrders().setVisible(true);
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

package mainPackage;

import common.OpenPdf;
import dao.ConnectionProvider;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ViewCustomerOrders extends JFrame implements ActionListener, MouseListener {

    private int customerPk = 0;

    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTable tableOrders = new JTable();
    JButton btnClose = new JButton();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();

    public ViewCustomerOrders() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public ViewCustomerOrders(String CustomerPk) {
        initComponents();
        setLocationRelativeTo(null);
        customerPk = Integer.parseInt(CustomerPk);
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        setLayout(null);
        setSize(850, 600);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setFont(new Font("Impact", 1, 36));
        jLabel1.setText("View Orders");
        add(jLabel1);
        jLabel1.setBounds(327, 6, 187, 45);

        tableOrders.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Order ID", "Date", "Total Paid"
                }
        ));
        jScrollPane2.setViewportView(tableOrders);
        tableOrders.addMouseListener(this);

        add(jScrollPane2);
        jScrollPane2.setBounds(220, 90, 408, 402);

        btnClose.setFont(new Font("Tahoma", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(760, 560, 72, 25);
        btnClose.addActionListener(this);

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setText("Order List");
        add(jLabel3);
        jLabel3.setBounds(390, 70, 70, 15);

        jLabel4.setIcon(new ImageIcon(getClass().getResource("/images/All_page_Background.png")));
        add(jLabel4);
        jLabel4.setBounds(0, 0, 850, 600);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnClose)) {
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent d) {
        if (d.getSource().equals(tableOrders)) {
            int index = tableOrders.getSelectedRow();
            TableModel model = tableOrders.getModel();
            String orderId = model.getValueAt(index, 0).toString();
            OpenPdf.OpenByIdForCustomer(orderId);
        }
    }

    private void formComponentShown(ComponentEvent evt) {
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableOrders.getModel();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from orderDetail where customer_fk=" + customerPk + "");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("orderId"), rs.getString("orderDate"), rs.getString("totalPaid")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        new ViewCustomerOrders().setVisible(true);
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

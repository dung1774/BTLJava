package mainPackage;

import dao.ConnectionProvider;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ManageProduct extends JFrame implements ActionListener, MouseListener {

    private int productPk = 0;
    private int totalQuantity = 0;

    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable tableProduct = new JTable();
    JLabel jLabel2 = new JLabel();
    JTextField txtName = new JTextField();
    JLabel lblQuantity = new JLabel();
    JTextField txtQuantity = new JTextField();
    JLabel jLabel4 = new JLabel();
    JTextField txtPrice = new JTextField();
    JLabel jLabel5 = new JLabel();
    JTextField txtDescription = new JTextField();
    JLabel jLabel6 = new JLabel();
    JComboBox comboBoxCategory = new JComboBox<>();
    JButton btnSave = new JButton();
    JButton btnUpdate = new JButton();
    JButton btnReset = new JButton();
    JButton btnClose = new JButton();
    JLabel jLabel3 = new JLabel();

    public ManageProduct() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private boolean validateFields(String formType) {
        if (formType.equals("edit") && !txtName.getText().equals("") && !txtPrice.getText().equals("") && !txtDescription.getText().equals("")) {
            return false;
        } else if (formType.equals("new") && !txtName.getText().equals("") && !txtPrice.getText().equals("") && !txtDescription.getText().equals("") && !txtQuantity.getText().equals("")) {
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
        jLabel1.setText("Manage Product");
        add(jLabel1);
        jLabel1.setBounds(280, 10, 260, 59);

        tableProduct.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Quantity", "Price", "Description", "Category ID", "Category Name"
                }
        ));
        jScrollPane1.setViewportView(tableProduct);
        tableProduct.addMouseListener(this);

        add(jScrollPane1);
        jScrollPane1.setBounds(6, 80, 452, 402);

        jLabel2.setFont(new Font("Segoe UI", 1, 12));
        jLabel2.setText("Name");
        add(jLabel2);
        jLabel2.setBounds(500, 100, 37, 16);

        txtName.setFont(new Font("Segoe UI", 1, 12));
        add(txtName);
        txtName.setBounds(500, 130, 320, 27);

        lblQuantity.setFont(new Font("Segoe UI", 1, 12));
        lblQuantity.setText("Quantity");
        add(lblQuantity);
        lblQuantity.setBounds(500, 170, 90, 16);

        txtQuantity.setFont(new Font("Segoe UI", 1, 12));
        add(txtQuantity);
        txtQuantity.setBounds(500, 200, 320, 27);

        jLabel4.setFont(new Font("Segoe UI", 1, 12));
        jLabel4.setText("Price");
        add(jLabel4);
        jLabel4.setBounds(500, 240, 37, 16);

        txtPrice.setFont(new Font("Segoe UI", 1, 12));
        add(txtPrice);
        txtPrice.setBounds(500, 270, 320, 27);

        jLabel5.setFont(new Font("Segoe UI", 1, 12));
        jLabel5.setText("Description");
        add(jLabel5);
        jLabel5.setBounds(500, 310, 336, 16);

        txtDescription.setFont(new Font("Segoe UI", 1, 12));
        add(txtDescription);
        txtDescription.setBounds(500, 350, 320, 27);

        jLabel6.setFont(new Font("Segoe UI", 1, 12));
        jLabel6.setText("Category");
        add(jLabel6);
        jLabel6.setBounds(500, 390, 72, 16);

        comboBoxCategory.setFont(new Font("Segoe UI", 1, 12));
        comboBoxCategory.setModel(new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        add(comboBoxCategory);
        comboBoxCategory.setBounds(500, 420, 320, 27);

        btnSave.setFont(new Font("Segoe UI", 1, 12));
        btnSave.setText("Save");
        add(btnSave);
        btnSave.setBounds(500, 460, 72, 27);
        btnSave.addActionListener(this);

        btnUpdate.setFont(new Font("Segoe UI", 1, 12));
        btnUpdate.setText("Update");
        add(btnUpdate);
        btnUpdate.setBounds(580, 460, 72, 27);
        btnUpdate.addActionListener(this);

        btnReset.setFont(new Font("Segoe UI", 1, 12));
        btnReset.setText("Reset");
        add(btnReset);
        btnReset.setBounds(660, 460, 72, 27);
        btnReset.addActionListener(this);

        btnClose.setFont(new Font("Segoe UI", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(740, 460, 80, 27);
        btnClose.addActionListener(this);

        jLabel3.setIcon(new ImageIcon(getClass().getResource("/images/All_page_Background.png")));
        add(jLabel3);
        jLabel3.setBounds(0, 0, 850, 600);
    }

    private void getAllCategory() {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from category");
            comboBoxCategory.removeAllItems();
            while (rs.next()) {
                comboBoxCategory.addItem(rs.getString("category_pk") + "-" + rs.getString("name"));
                // 1-TestCategory
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void formComponentShown(ComponentEvent evt) {
        // TODO add your handling code here:
        getAllCategory();
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from product inner join category on product.category_fk = category.category_pk");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("product_pk"), rs.getString("name"), rs.getString("quantity"), rs.getString("price"), rs.getString("description"), rs.getString("category_fk"), rs.getString(8)});
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
        new ManageProduct().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnSave)) {
            String name = txtName.getText();
            String quantity = txtQuantity.getText();
            String price = txtPrice.getText();
            String description = txtDescription.getText();
            String category = (String) comboBoxCategory.getSelectedItem();
            String categoryID[] = category.split("-", 0);
            if (validateFields("new")) {
                JOptionPane.showMessageDialog(null, "All fields are required");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("insert into product (name,quantity,price,description,category_fk) values(?,?,?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, quantity);
                    ps.setString(3, price);
                    ps.setString(4, description);
                    ps.setString(5, categoryID[0]);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Added Succesfully");
                    setVisible(false);
                    new ManageProduct().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnUpdate)) {
            String name = txtName.getText();
            String quantity = txtQuantity.getText();
            String price = txtPrice.getText();
            String description = txtDescription.getText();
            String category = (String) comboBoxCategory.getSelectedItem();
            String categoryId[] = category.split("-", 0);
            if (validateFields("edit")) {
                JOptionPane.showMessageDialog(null, "All fields are required");
            } else {
                try {
                    if (!quantity.equals("")) {
                        totalQuantity = totalQuantity + Integer.parseInt(quantity);
                    }
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("update product set name=?,quantity=?,price=?,description=?,category_fk=? where product_pk=?");
                    ps.setString(1, name);
                    ps.setInt(2, totalQuantity);
                    ps.setString(3, price);
                    ps.setString(4, description);
                    ps.setString(5, categoryId[0]);
                    ps.setInt(6, productPk);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Updated Succesfully");
                    setVisible(false);
                    new ManageProduct().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnReset)) {
            setVisible(false);
            new ManageProduct().setVisible(true);
        }

        if (d.getSource().equals(btnClose)) {
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent d) {
        if (d.getSource().equals(tableProduct)) {
            int index = tableProduct.getSelectedRow();
            TableModel model = tableProduct.getModel();
            String id = model.getValueAt(index, 0).toString();
            productPk = Integer.parseInt(id);

            String name = model.getValueAt(index, 1).toString();
            txtName.setText(name);

            String quantity = model.getValueAt(index, 2).toString();
            totalQuantity = 0;
            lblQuantity.setText("Add Quantity");
            totalQuantity = Integer.parseInt(quantity);

            String price = model.getValueAt(index, 3).toString();
            txtPrice.setText(price);

            String description = model.getValueAt(index, 4).toString();
            txtDescription.setText(description);

            comboBoxCategory.removeAllItems();
            String categoryId = model.getValueAt(index, 5).toString();
            String categoryName = model.getValueAt(index, 6).toString();
            comboBoxCategory.addItem(categoryId + "-" + categoryName);
            try {
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select *from category");
                while (rs.next()) {
                    if (Integer.parseInt(categoryId) != rs.getInt(1)) {
                        comboBoxCategory.addItem(rs.getString("category_pk") + "-" + rs.getString("name"));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

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

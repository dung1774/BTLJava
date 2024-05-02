package mainPackage;

import dao.ConnectionProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ManageCategory extends JFrame implements ActionListener, MouseListener {

    private int categoryPk = 0;

    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable tableCategory = new JTable();
    JTextField txtName = new JTextField();
    JButton btnSave = new JButton();
    JButton btnUpdate = new JButton();
    JButton btnDelete = new JButton();
    JButton btnReset = new JButton();
    JButton btnClose = new JButton();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();

    public ManageCategory() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private boolean validateFields() {
        if (!txtName.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(null);

        setSize(928, 600);

        jLabel1.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        jLabel1.setText("Manage Category");
        add(jLabel1);
        jLabel1.setBounds(287, 60, 300, 45);

        tableCategory.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name"
                }
        ));

        jScrollPane1.setViewportView(tableCategory);
        tableCategory.addMouseListener(this);

        add(jScrollPane1);
        jScrollPane1.setBounds(16, 173, 425, 402);

        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        add(txtName);
        txtName.setBounds(459, 316, 430, 25);

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setText("Save ");
        add(btnSave);
        btnSave.setBounds(459, 374, 72, 25);
        btnSave.addActionListener(this);

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setText("Update");
        add(btnUpdate);
        btnUpdate.setBounds(549, 374, 72, 25);
        btnUpdate.addActionListener(this);

        btnClose.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(821, 374, 70, 25);
        btnClose.addActionListener(this);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Name");
        add(jLabel2);
        jLabel2.setBounds(459, 262, 50, 17);

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        add(btnReset);
        btnReset.setBounds(731, 374, 70, 25);
        btnReset.addActionListener(this);

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.setText("Delete");
        add(btnDelete);
        btnDelete.setBounds(641, 374, 70, 25);
        btnDelete.addActionListener(this);

        jLabel3.setIcon(new ImageIcon(getClass().getResource("/images/Orders_background.png"))); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(0, 0, 928, 600);
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
        new ManageCategory().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnSave)) {
            String name = txtName.getText();
            if (validateFields()) {
                JOptionPane.showMessageDialog(null, "Tất cả các trường không được để trống!");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select *from category where name = '" + name + "'");
                    while (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Danh mục này đã tồn tại!");
                        return;
                    }
                    PreparedStatement ps = con.prepareStatement("insert into category (name) values(?)");
                    ps.setString(1, name);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Thêm danh mục thành công!");
                    setVisible(false);
                    new ManageCategory().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnUpdate)) {
            String name = txtName.getText();
            if (validateFields()) {
                JOptionPane.showMessageDialog(null, "Tất cả các trường không được để trống!");
            } else {
                try {
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("update category set name=? where category_pk=?");
                    ps.setString(1, name);
                    ps.setInt(2, categoryPk);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cập nhật danh mục thành công!");
                    setVisible(false);
                    new ManageCategory().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (d.getSource().equals(btnDelete)) {
            try {
                int index = tableCategory.getSelectedRow();
                TableModel model = tableCategory.getModel();
                String id = model.getValueAt(index, 0).toString();
                try {
                    Connection con = ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá danh mục này không?", "Select", JOptionPane.YES_NO_OPTION);
                    int b = JOptionPane.showConfirmDialog(null, "nếu xoá danh mục thì toàn bộ sản phẩm thuộc danh mục này cũng sẽ bị xoá! Bạn có chắc chắn muốn xoá?", "Select", JOptionPane.YES_NO_OPTION);
                    if (a == 0 && b == 0) {
                        int rowsAffected = st.executeUpdate("delete from category where category_pk='" + id + "'");
                        int rowsAffected2 = st.executeUpdate("delete from product where category_fk='" + id + "'");
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "xoá danh mục sản phẩm thành công!");
                            setVisible(false);
                            new ManageCategory().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "không tìm thầy danh mục sản phẩm tương ứng để xoá!.");
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Chọn ít nhất 1 danh mục sản phẩm để xoá!");
            }
        }

        if (d.getSource().equals(btnClose)) {
            setVisible(false);
        }

        if (d.getSource().equals(btnReset)) {
            setVisible(false);
            new ManageCategory().setVisible(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(tableCategory)) {
            int index = tableCategory.getSelectedRow();
            TableModel model = tableCategory.getModel();
            String id = model.getValueAt(index, 0).toString();
            categoryPk = Integer.parseInt(id);

            String name = model.getValueAt(index, 1).toString();
            txtName.setText(name);
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
        }
    }

    private void formComponentShown(ComponentEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tableCategory.getModel();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select *from category");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("category_pk"), rs.getString("name")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        btnUpdate.setEnabled(false);
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

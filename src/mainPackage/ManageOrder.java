package mainPackage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import common.OpenPdf;
import dao.ConnectionProvider;
import dao.InventoryUtils;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.TableModel;

public class ManageOrder extends JFrame implements ActionListener, MouseListener {

    private int currentCustomerId = -1;

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel10 = new JLabel();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();
    JLabel jLabel13 = new JLabel();
    JLabel jLabel14 = new JLabel();
    JLabel jLabel15 = new JLabel();
    JLabel jLabel16 = new JLabel();
    JLabel lbFinalTotalPrice = new JLabel();

    JTextField txtCustomerName = new JTextField();
    JTextField txtCustomerMobileNumber = new JTextField();
    JTextField txtCustomerEmail = new JTextField();
    JTextField txtProductPrice = new JTextField();
    JTextField txtProductDescription = new JTextField();
    JTextField txtProductName = new JTextField();
    JTextField txtOrderQuantity = new JTextField();

    JButton btnAddToCart = new JButton();
    JButton btnReset = new JButton();
    JButton btnClose = new JButton();
    JButton btnDelete = new JButton();
    JButton btnSaveOrderDetails = new JButton();

    JTable tableCustomer = new JTable();
    JTable tableProduct = new JTable();
    JTable tableCart = new JTable();

    JScrollPane jScrollPane1 = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JScrollPane jScrollPane3 = new JScrollPane();

    private int customerPk = 0;
    private int productPk = 0;
    private int finalTotalPrice = 0;
    private String orderId = "";

    public ManageOrder() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void clearProductFields() {
        productPk = 0;
        txtProductName.setText("");
        txtProductPrice.setText("");
        txtProductDescription.setText("");
        txtOrderQuantity.setText("");
    }

    public String getUniqueId(String prefix) {
        return prefix + System.nanoTime();
    }

    private void initComponents() {

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel11.setIcon(new ImageIcon(getClass().getResource("/images/Orders_background.png")));

        jLabel1.setFont(new Font("Impact", 1, 36));
        jLabel1.setText("Manage Order");

        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jLabel2.setText("Customer List");

        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setText("Cart");

        jLabel5.setFont(new Font("Tahoma", 1, 12));
        jLabel5.setText("Selected Customer:");

        jLabel6.setFont(new Font("Tahoma", 1, 12));
        jLabel6.setText("Name");

        jLabel15.setFont(new Font("Tahoma", 1, 12));
        jLabel15.setText("Order Quantity");

        jLabel16.setFont(new Font("Tahoma", 1, 12));
        jLabel16.setText("Product List");

        jLabel14.setFont(new Font("Tahoma", 1, 18));
        jLabel14.setText("Tổng tiền:");

        lbFinalTotalPrice.setFont(new Font("Tahoma", 1, 18));
        lbFinalTotalPrice.setText("00000 VND");

        jLabel8.setFont(new Font("Tahoma", 1, 12));
        jLabel8.setText("Email");

        jLabel7.setFont(new Font("Tahoma", 1, 12));
        jLabel7.setText("Mobile Number");

        jLabel10.setFont(new Font("Tahoma", 1, 12));
        jLabel10.setText("Product Name");

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setText("Product Price");

        jLabel9.setFont(new Font("Tahoma", 1, 12));
        jLabel9.setText("Selected Product:");

        jLabel13.setFont(new Font("Tahoma", 1, 12));
        jLabel13.setText("Product Description");

        txtProductName.setFont(new Font("Tahoma", 1, 12));

        txtProductPrice.setFont(new Font("Tahoma", 1, 12));

        txtProductDescription.setFont(new Font("Tahoma", 1, 12));

        txtCustomerName.setFont(new Font("Tahoma", 1, 12));

        txtCustomerMobileNumber.setFont(new Font("Tahoma", 1, 12));

        txtCustomerEmail.setFont(new Font("Tahoma", 1, 12));

        btnSaveOrderDetails.setFont(new Font("Tahoma", 1, 12));
        btnSaveOrderDetails.setText("Save Order Details");

        btnReset.setFont(new Font("Tahoma", 1, 12));
        btnReset.setText("Reset");

        btnClose.setFont(new Font("Tahoma", 1, 12));
        btnClose.setText("Close");

        btnDelete.setFont(new Font("Tahoma", 1, 12));
        btnDelete.setText("Delete");

        btnAddToCart.setFont(new Font("Tahoma", 1, 12));
        btnAddToCart.setText("Add To Cart");

        btnAddToCart.addActionListener(this);
        btnDelete.addActionListener(this);
        btnReset.addActionListener(this);
        btnClose.addActionListener(this);
        btnSaveOrderDetails.addActionListener(this);

        tableCustomer.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Mobile Number", "Email"
                }
        ));

        jScrollPane1.setViewportView(tableCustomer);

        tableProduct.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Price", "Quantity", "Description", "Category ID", "Category Name"
                }
        ));

        jScrollPane2.setViewportView(tableProduct);

        tableCart.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Customer ID", "Product ID", "Name", "Quantity", "Price", "Description", "Sub Total"
                }
        ));

        jScrollPane3.setViewportView(tableCart);

        tableProduct.addMouseListener(this);
        tableCustomer.addMouseListener(this);
        tableCart.addMouseListener(this);

        setLayout(null);
        setSize(1368, 768);
        setUndecorated(true);

        add(jLabel1);
        jLabel1.setBounds(590, 20, 230, 45);

        add(jLabel2);
        jLabel2.setBounds(140, 90, 90, 15);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 110, 340, 270);

        add(jLabel4);
        jLabel4.setBounds(1090, 90, 30, 15);

        add(jScrollPane3);
        jScrollPane3.setBounds(870, 110, 470, 270);
        
        add(jLabel5);
        jLabel5.setBounds(21, 386, 116, 15);
        
        add(jLabel6);
        jLabel6.setBounds(21, 420, 37, 15);
        
        add(txtCustomerName);
        txtCustomerName.setBounds(21, 459, 340, 27);
        
        add(jLabel7);
        jLabel7.setBounds(21, 499, 90, 15);
        
        add(txtCustomerMobileNumber);
        txtCustomerMobileNumber.setBounds(21, 533, 340, 27);
        
        add(jLabel8);
        jLabel8.setBounds(21, 573, 37, 15);
        
        add(txtCustomerEmail);
        txtCustomerEmail.setBounds(21, 607, 340, 27);
        
        add(jLabel14);
        jLabel14.setBounds(1010, 390, 170, 22);
        
        add(jLabel15);
        jLabel15.setBounds(390, 650, 120, 15);
        
        add(lbFinalTotalPrice);
        lbFinalTotalPrice.setBounds(1120, 390, 150, 25);
        
        add(btnSaveOrderDetails);
        btnSaveOrderDetails.setBounds(870, 460, 470, 27);
        
        add(btnReset);
        btnReset.setBounds(870, 530, 470, 27);
        
        add(btnClose);
        btnClose.setBounds(870, 680, 470, 27);
        
        add(btnDelete);
        btnDelete.setBounds(870, 610, 470, 27);
        
        add(txtProductPrice);
        txtProductPrice.setBounds(390, 530, 450, 27);
        
        add(jLabel16);
        jLabel16.setBounds(580, 90, 72, 15);
        
        add(txtProductDescription);
        txtProductDescription.setBounds(390, 610, 450, 27);
        
        add(jLabel12);
        jLabel12.setBounds(390, 570, 118, 15);
        
        add(btnAddToCart);
        btnAddToCart.setBounds(390, 720, 450, 27);
        
        add(jLabel13);
        jLabel13.setBounds(390, 573, 120, 15);
        
        add(jScrollPane2);
        jScrollPane2.setBounds(390, 110, 450, 270);
        
        add(jLabel9);
        jLabel9.setBounds(390, 390, 110, 15);
        
        add(txtProductName);
        txtProductName.setBounds(390, 460, 450, 27);
        
        add(jLabel10);
        jLabel10.setBounds(390, 420, 90, 15);
        
        add(jLabel3);
        jLabel3.setBounds(390, 500, 100, 15);
        
        add(txtOrderQuantity);
        txtOrderQuantity.setBounds(390, 680, 450, 27);
        
        add(jLabel11);
        jLabel11.setBounds(0, 0, 1368, 768);
    }

    private void formComponentShown(ComponentEvent evt) {
        txtCustomerName.setEditable(false);
        txtCustomerMobileNumber.setEditable(false);
        txtCustomerEmail.setEditable(false);

        txtProductName.setEditable(false);
        txtProductPrice.setEditable(false);
        txtProductDescription.setEditable(false);

        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        DefaultTableModel productModel = (DefaultTableModel) tableProduct.getModel();

        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from customer");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("customer_pk"), rs.getString("name"), rs.getString("mobileNumber"), rs.getString("email")});
            }

            rs = st.executeQuery("select *from product inner join category on product.category_fk = category.category_pk");
            while (rs.next()) {
                productModel.addRow(new Object[]{rs.getString("product_pk"), rs.getString("name"), rs.getString("price"), rs.getString("quantity"),
                    rs.getString("description"), rs.getString("category_fk"), rs.getString(8)});
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

        if (d.getSource().equals(btnReset)) {
            setVisible(false);
            new ManageOrder().setVisible(true);
        }

        if (d.getSource().equals(btnSaveOrderDetails)) {
            if (finalTotalPrice != 0 && !txtCustomerName.getText().equals("")) {
                orderId = getUniqueId("Bill-");
                DefaultTableModel dtm = (DefaultTableModel) tableCart.getModel();
                if (tableCart.getRowCount() != 0) {
                    for (int i = 0; i < tableCart.getRowCount(); i++) {
                        try {
                            Connection con = ConnectionProvider.getCon();
                            Statement st = con.createStatement();
                            st.execute("update product set quantity=quantity-" + Integer.parseInt(dtm.getValueAt(i, 3).toString()) + " where product_pk=" + Integer.parseInt(dtm.getValueAt(i, 1).toString()));
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }

                try {
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    Connection con = ConnectionProvider.getCon();
                    PreparedStatement ps = con.prepareStatement("insert into orderDetail(orderId,customer_fk,orderDate,totalPaid) values (?,?,?,?)");
                    ps.setString(1, orderId);
                    ps.setInt(2, customerPk);
                    ps.setString(3, myFormat.format(cal.getTime()));
                    ps.setInt(4, finalTotalPrice);
                    ps.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                //Creating Document
                com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
                try {
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    PdfWriter.getInstance(doc, new FileOutputStream(InventoryUtils.billPath + "" + orderId + ".pdf"));
                    doc.open();
                    Paragraph projectname = new Paragraph("										Inventory Management System\n");
                    doc.add(projectname);
                    Paragraph starLine = new Paragraph("**************************************************************************************************************************************");
                    doc.add(starLine);
                    Paragraph details = new Paragraph("\tOrderID: " + orderId + "\nDate: " + myFormat.format(cal.getTime()) + "\nTotal Paid: " + finalTotalPrice);
                    doc.add(details);
                    doc.add(starLine);
                    PdfPTable tb1 = new PdfPTable(5);
                    PdfPCell nameCell = new PdfPCell(new Phrase("Name"));
                    PdfPCell descriptionCell = new PdfPCell(new Phrase("Description"));
                    PdfPCell priceCell = new PdfPCell(new Phrase("Price Per Unit"));
                    PdfPCell quantityCell = new PdfPCell(new Phrase("Quantity"));
                    PdfPCell subTotalPriceCell = new PdfPCell(new Phrase("Sub Total Price"));

                    BaseColor backgroundColor = new BaseColor(255, 204, 51);
                    nameCell.setBackgroundColor(backgroundColor);
                    descriptionCell.setBackgroundColor(backgroundColor);
                    priceCell.setBackgroundColor(backgroundColor);
                    quantityCell.setBackgroundColor(backgroundColor);
                    subTotalPriceCell.setBackgroundColor(backgroundColor);

                    tb1.addCell(nameCell);
                    tb1.addCell(descriptionCell);
                    tb1.addCell(priceCell);
                    tb1.addCell(quantityCell);
                    tb1.addCell(subTotalPriceCell);

                    for (int i = 0; i < tableCart.getRowCount(); i++) {
                        tb1.addCell(tableCart.getValueAt(i, 2).toString());
                        tb1.addCell(tableCart.getValueAt(i, 5).toString());
                        tb1.addCell(tableCart.getValueAt(i, 4).toString());
                        tb1.addCell(tableCart.getValueAt(i, 3).toString());
                        tb1.addCell(tableCart.getValueAt(i, 6).toString());
                    }

                    doc.add(tb1);
                    doc.add(starLine);
                    Paragraph thanksMsg = new Paragraph("Thank you. Please visit again!");
                    doc.add(thanksMsg);
                    OpenPdf.OpenByIdForAdmin(orderId);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                doc.close();
                setVisible(false);
                new ManageOrder().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng thêm sản phẩm vào giỏ hàng!");
            }
        }

        if (d.getSource().equals(btnDelete)) {
            try {
                int index = tableCart.getSelectedRow();
                int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn gỡ sản phẩm khỏi giỏ hàng không?", "Select", JOptionPane.YES_NO_OPTION);
                if (a == 0) {
                    TableModel model = tableCart.getModel();
                    String subTotal = model.getValueAt(index, 6).toString();
                    finalTotalPrice = finalTotalPrice - Integer.parseInt(subTotal);
                    lbFinalTotalPrice.setText(String.valueOf(finalTotalPrice)+" VND");
                    ((DefaultTableModel) tableCart.getModel()).removeRow(index);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Chọn ít nhất 1 sản phẩm để loại bỏ!");
            }
        }

        if (d.getSource().equals(btnAddToCart)) {
            String noOfUnits = txtOrderQuantity.getText();
            int totalQuantity = 0;
            if (!noOfUnits.equals("")) {
                String productName = txtProductName.getText();
                String productDescription = txtProductDescription.getText();
                String productPrice = txtProductPrice.getText();

                int totalPrice = Integer.parseInt(txtOrderQuantity.getText()) * Integer.parseInt(productPrice);
                int checkStock = 0;
                int checkProductAlreadyExistInCart = 0;
                int existingProductIndex = -1;

                try {
                    Connection con = ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select *from product where product_pk=" + productPk + "");
                    while (rs.next()) {
                        if (rs.getInt("quantity") >= Integer.parseInt(noOfUnits)) {
                            checkStock = 1;
                            totalQuantity = rs.getInt("quantity");
                        } else {
                            JOptionPane.showMessageDialog(null, "Đã hết hàng trong kho. chỉ còn lại " + rs.getInt("quantity") + " sản phẩm!");
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                if (checkStock == 1) {
                    // Kiểm tra nếu đã có khách hàng được chọn trước đó và customerPk của mục mới không khớp
                    if (currentCustomerId != -1 && currentCustomerId != customerPk) {
                        JOptionPane.showMessageDialog(null, "Mỗi lần chỉ 1 khách được đặt hàng!");
                        return; // Không thêm sản phẩm vào giỏ hàng nếu có lỗi xảy ra
                    }
                    DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
                    if (tableCart.getRowCount() != 0) {
                        for (int i = 0; i < tableCart.getRowCount(); i++) {
                            if (Integer.parseInt(model.getValueAt(i, 1).toString()) == productPk) {
                                checkProductAlreadyExistInCart = 1;
                                existingProductIndex = i;
                                JOptionPane.showMessageDialog(null, "Sản phẩm đã có trong giỏ hàng!");
                                try {
                                    int a = JOptionPane.showConfirmDialog(null, "Bạn vẫn muốn thêm sản phẩm này?", "Select", JOptionPane.YES_NO_OPTION);
                                    int existingQuantity = Integer.parseInt(model.getValueAt(existingProductIndex, 3).toString());
                                    int newQuantity = existingQuantity + Integer.parseInt(noOfUnits);
                                    if (a == 0 && newQuantity <= totalQuantity) {
                                        int newTotalPrice = newQuantity * Integer.parseInt(productPrice);
                                        model.setValueAt(newQuantity, existingProductIndex, 3);
                                        model.setValueAt(newTotalPrice, existingProductIndex, 6);
                                        finalTotalPrice += Integer.parseInt(noOfUnits) * Integer.parseInt(productPrice);
                                        lbFinalTotalPrice.setText(String.valueOf(finalTotalPrice)+" VND");
                                        JOptionPane.showMessageDialog(null, "Đã cập nhật thêm số lượng sản phẩm!");
                                    } else if (newQuantity > totalQuantity) {
                                        JOptionPane.showMessageDialog(null, "Đã hết hàng trong kho. chỉ còn lại " + (totalQuantity - existingQuantity) + " sản phẩm!");
                                    }
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                            }
                        }
                    }
                    if (checkProductAlreadyExistInCart == 0) {
                        // Cập nhật currentCustomerId nếu đây là lần đầu tiên sản phẩm được thêm vào giỏ hàng
                        if (currentCustomerId == -1) {
                            currentCustomerId = customerPk;
                        }
                        model.addRow(new Object[]{customerPk, productPk, productName, noOfUnits, productPrice, productDescription, totalPrice});
                        finalTotalPrice = finalTotalPrice + totalPrice;
                        lbFinalTotalPrice.setText(String.valueOf(finalTotalPrice)+" VND");
                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    }
                    clearProductFields();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cần nhập só lượng sản phẩm muốn mua!");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(tableCustomer)) {
            int index = tableCustomer.getSelectedRow();
            TableModel model = tableCustomer.getModel();
            String id = model.getValueAt(index, 0).toString();
            customerPk = Integer.parseInt(id);

            String name = model.getValueAt(index, 1).toString();
            txtCustomerName.setText(name);

            String mobileNumber = model.getValueAt(index, 2).toString();
            txtCustomerMobileNumber.setText(mobileNumber);

            String email = model.getValueAt(index, 3).toString();
            txtCustomerEmail.setText(email);
        }
        if (e.getSource().equals(tableProduct)) {
            int index = tableProduct.getSelectedRow();
            TableModel model = tableProduct.getModel();
            String id = model.getValueAt(index, 0).toString();
            productPk = Integer.parseInt(id);

            String productName = model.getValueAt(index, 1).toString();
            txtProductName.setText(productName);

            String productPrice = model.getValueAt(index, 2).toString();
            txtProductPrice.setText(productPrice);

            String productDescription = model.getValueAt(index, 4).toString();
            txtProductDescription.setText(productDescription);
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
        new ManageOrder().setVisible(true);
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

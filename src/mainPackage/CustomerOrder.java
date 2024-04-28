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

public class CustomerOrder extends JFrame implements ActionListener, MouseListener {

    JLabel jLabel4 = new JLabel();
    JScrollPane jScrollPane3 = new JScrollPane();
    JTable tableCart = new JTable();
    JLabel jLabel14 = new JLabel();
    JLabel lbFinalTotalPrice = new JLabel();
    JButton btnSaveOrderDetails = new JButton();
    JButton btnReset = new JButton();
    JButton btnClose = new JButton();
    JButton btnDelete = new JButton();
    JTextField txtProductPrice = new JTextField();
    JLabel jLabel16 = new JLabel();
    JTextField txtProductDescription = new JTextField();
    JLabel jLabel12 = new JLabel();
    JButton btnAddToCart = new JButton();
    JLabel jLabel13 = new JLabel();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTable tableProduct = new JTable();
    JLabel jLabel9 = new JLabel();
    JTextField txtProductName = new JTextField();
    JLabel jLabel10 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JTextField txtOrderQuantity = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();

    private int customerPk = 0;
    private int productPk = 0;
    private int finalTotalPrice = 0;
    private String orderId = "";

    public CustomerOrder(String CustomerPk) {
        initComponents();
        setLocationRelativeTo(null);
        customerPk = Integer.parseInt(CustomerPk);
    }
    
    public CustomerOrder() {
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
        setSize(1100, 768);

        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setText("Cart");
        add(jLabel4);
        jLabel4.setBounds(770, 80, 30, 15);

        tableCart.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Customer ID", "Product ID", "Name", "Quantity", "Price", "Description", "Sub Total"
                }
        ));
        jScrollPane3.setViewportView(tableCart);
        tableCart.addMouseListener(this);

        add(jScrollPane3);
        jScrollPane3.setBounds(550, 100, 470, 250);

        jLabel14.setFont(new Font("Tahoma", 1, 18));
        jLabel14.setText("Total Amount RS:");
        add(jLabel14);
        jLabel14.setBounds(690, 380, 170, 25);

        lbFinalTotalPrice.setFont(new Font("Tahoma", 1, 18));
        lbFinalTotalPrice.setText("00000");
        add(lbFinalTotalPrice);
        lbFinalTotalPrice.setBounds(1180, 390, 70, 25);

        btnSaveOrderDetails.setFont(new Font("Tahoma", 1, 12));
        btnSaveOrderDetails.setText("Save Order Details");
        add(btnSaveOrderDetails);
        btnSaveOrderDetails.setBounds(550, 450, 470, 25);
        btnSaveOrderDetails.addActionListener(this);

        btnReset.setFont(new Font("Tahoma", 1, 12));
        btnReset.setText("Reset");
        add(btnReset);
        btnReset.setBounds(550, 520, 470, 25);
        btnReset.addActionListener(this);

        btnClose.setFont(new Font("Tahoma", 1, 12));
        btnClose.setText("Close");
        add(btnClose);
        btnClose.setBounds(550, 670, 470, 25);
        btnClose.addActionListener(this);

        btnDelete.setFont(new Font("Tahoma", 1, 12));
        btnDelete.setText("Delete");
        add(btnDelete);
        btnDelete.setBounds(550, 600, 470, 25);
        btnDelete.addActionListener(this);

        txtProductPrice.setFont(new Font("Tahoma", 1, 12));
        add(txtProductPrice);
        txtProductPrice.setBounds(70, 520, 450, 25);

        jLabel16.setFont(new Font("Tahoma", 1, 12));
        jLabel16.setText("Product List");
        add(jLabel16);
        jLabel16.setBounds(260, 80, 75, 15);

        txtProductDescription.setFont(new Font("Tahoma", 1, 12));
        add(txtProductDescription);
        txtProductDescription.setBounds(70, 600, 450, 25);

        jLabel12.setFont(new Font("Tahoma", 1, 12));
        jLabel12.setText("Product Description");
        add(jLabel12);
        jLabel12.setBounds(70, 560, 125, 15);

        btnAddToCart.setFont(new Font("Tahoma", 1, 12));
        btnAddToCart.setText("Add To Cart");
        add(btnAddToCart);
        btnAddToCart.setBounds(70, 710, 450, 25);
        btnAddToCart.addActionListener(this);

        jLabel13.setFont(new Font("Tahoma", 1, 12));
        jLabel13.setText("Order Quantity");
        add(jLabel13);
        jLabel13.setBounds(70, 640, 92, 15);

        tableProduct.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Name", "Price", "Quantity", "Description", "Category ID", "Category Name"
                }
        ));
        jScrollPane2.setViewportView(tableProduct);
        tableProduct.addMouseListener(this);

        add(jScrollPane2);
        jScrollPane2.setBounds(70, 100, 450, 250);

        jLabel9.setFont(new Font("Tahoma", 1, 12));
        jLabel9.setText("Selected Product:");
        add(jLabel9);
        jLabel9.setBounds(70, 380, 110, 15);

        txtProductName.setFont(new Font("Tahoma", 1, 12));
        add(txtProductName);
        txtProductName.setBounds(70, 450, 450, 25);

        jLabel10.setFont(new Font("Tahoma", 1, 12));
        jLabel10.setText("Product Name");
        add(jLabel10);
        jLabel10.setBounds(70, 410, 90, 15);

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setText("Product Price");
        add(jLabel3);
        jLabel3.setBounds(70, 490, 100, 15);
        add(txtOrderQuantity);
        txtOrderQuantity.setBounds(70, 670, 450, 25);

        jLabel1.setFont(new Font("Impact", 1, 36));
        jLabel1.setText("Order");
        add(jLabel1);
        jLabel1.setBounds(480, 20, 100, 45);

        jLabel2.setIcon(new ImageIcon(getClass().getResource("/images/Orders_background.png")));
        jLabel2.setText("jLabel2");
        add(jLabel2);
        jLabel2.setBounds(0, 0, 1100, 770);
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

    private void formComponentShown(ComponentEvent evt) {

        txtProductName.setEditable(false);
        txtProductPrice.setEditable(false);
        txtProductDescription.setEditable(false);

        DefaultTableModel productModel = (DefaultTableModel) tableProduct.getModel();

        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from product inner join category on product.category_fk = category.category_pk");
            while (rs.next()) {
                productModel.addRow(new Object[]{rs.getString("product_pk"), rs.getString("name"), rs.getString("price"), rs.getString("quantity"),
                    rs.getString("description"), rs.getString("category_fk"), rs.getString(8)});
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
        new CustomerOrder().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent d) {
        if (d.getSource().equals(btnSaveOrderDetails)) {
            if (finalTotalPrice != 0 ) {
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
                    Paragraph thanksMsg = new Paragraph("Thank you, Please visit again.");
                    doc.add(thanksMsg);
                    OpenPdf.OpenByIdForCustomer(orderId);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                doc.close();
                setVisible(false);
                new CustomerOrder().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please add some product to cart");
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
                            JOptionPane.showMessageDialog(null, "Product is out of stock. Only " + rs.getInt("quantity") + " Left");
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                if (checkStock == 1) {
                    DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
                    if (tableCart.getRowCount() != 0) {
                        for (int i = 0; i < tableCart.getRowCount(); i++) {
                            if (Integer.parseInt(model.getValueAt(i, 1).toString()) == productPk) {
                                checkProductAlreadyExistInCart = 1;
                                existingProductIndex = i;
                                JOptionPane.showMessageDialog(null, "Product already exist in cart");
                                try {
                                    int a = JOptionPane.showConfirmDialog(null, "Do you still want to add this product", "Select", JOptionPane.YES_NO_OPTION);
                                    int existingQuantity = Integer.parseInt(model.getValueAt(existingProductIndex, 3).toString());
                                    int newQuantity = existingQuantity + Integer.parseInt(noOfUnits);
                                    if (a == 0 && newQuantity <= totalQuantity) {
                                        int newTotalPrice = newQuantity * Integer.parseInt(productPrice);
                                        model.setValueAt(newQuantity, existingProductIndex, 3);
                                        model.setValueAt(newTotalPrice, existingProductIndex, 6);
                                        finalTotalPrice += Integer.parseInt(noOfUnits) * Integer.parseInt(productPrice);
                                        lbFinalTotalPrice.setText(String.valueOf(finalTotalPrice));
                                        JOptionPane.showMessageDialog(null, "the amount of product has been updated");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Product is out of stock. Only " + (totalQuantity - existingQuantity) + " Left");
                                    }
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                            }
                        }
                    }
                    if (checkProductAlreadyExistInCart == 0) {
                        model.addRow(new Object[]{customerPk, productPk, productName, noOfUnits, productPrice, productDescription, totalPrice});
                        finalTotalPrice = finalTotalPrice + totalPrice;
                        lbFinalTotalPrice.setText(String.valueOf(finalTotalPrice));
                        JOptionPane.showMessageDialog(null, "Added successfully");
                    }
                    clearProductFields();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No of quantity and product field is required");
            }
        }

        if (d.getSource().equals(btnDelete)) {
            try {
                int index = tableCart.getSelectedRow();
                int a = JOptionPane.showConfirmDialog(null, "Do you want to remove this product", "Select", JOptionPane.YES_NO_OPTION);
                if (a == 0) {
                    TableModel model = tableCart.getModel();
                    String subTotal = model.getValueAt(index, 6).toString();
                    finalTotalPrice = finalTotalPrice - Integer.parseInt(subTotal);
                    lbFinalTotalPrice.setText(String.valueOf(finalTotalPrice));
                    ((DefaultTableModel) tableCart.getModel()).removeRow(index);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "choose at least one product to remove");
            }
        }

        if (d.getSource().equals(btnReset)) {
            setVisible(false);
            new CustomerOrder().setVisible(true);
        }

        if (d.getSource().equals(btnClose)) {
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent d) {
        if (d.getSource().equals(tableCart)) {

        }

        if (d.getSource().equals(tableProduct)) {
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

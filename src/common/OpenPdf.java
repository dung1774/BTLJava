package common;

import dao.InventoryUtils;
import java.io.File;
import javax.swing.JOptionPane;

public class OpenPdf {

    public static boolean checkBill = false;

    public static void OpenByIdForAdmin(String id) {
        try {
            if ((new File(InventoryUtils.billPath + id + ".pdf")).exists()) {
                Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + InventoryUtils.billPath + "" + id + ".pdf");
                checkBill = false;
            } else {
                JOptionPane.showMessageDialog(null, "hoá đơn không tồn tại!");
                int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá file này không?", "Select", JOptionPane.YES_NO_OPTION);
                if (a == 0) {
                    checkBill = true;
                } else {
                    checkBill = false;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void OpenByIdForCustomer(String id) {
        try {
            if ((new File(InventoryUtils.billPath + id + ".pdf")).exists()) {
                Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + InventoryUtils.billPath + "" + id + ".pdf");
            } else {
                JOptionPane.showMessageDialog(null, "hoá đơn không tồn tại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}

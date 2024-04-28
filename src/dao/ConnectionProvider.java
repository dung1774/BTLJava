package dao;
import java.sql.*;

public class ConnectionProvider {
    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory?useSSL=false","root","0919912845");
            return con;
        } catch (Exception e) {
            throw new RuntimeException("unhandled", e);
        }
    }
}

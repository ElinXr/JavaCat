
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierDAO {
    public void addSupplier(String supplierName, String contactInfo) {
        String sql = "INSERT INTO Suppliers (supplier_name, contact_info) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplierName);
            stmt.setString(2, contactInfo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

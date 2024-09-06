
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {
    public void addProduct(String productName, int categoryId, int supplierId, int quantity, double price) {
        String sql = "INSERT INTO Products (product_name, category_id, supplier_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productName);
            stmt.setInt(2, categoryId);
            stmt.setInt(3, supplierId);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

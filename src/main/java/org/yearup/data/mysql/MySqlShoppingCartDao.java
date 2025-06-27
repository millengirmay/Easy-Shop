package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao  extends MySqlDaoBase implements ShoppingCartDao {
    private  ProductDao productDao;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource,ProductDao productdao){
        super(dataSource);
        this.productDao = productdao;
    }
    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql =
                "SELECT shopping_cart.product_id, shopping_cart.quantity, " +
                        "products.name, products.price, products.category_id, products.description, " +
                        "products.color, products.stock, products.featured, products.image_url " +
                        "FROM shopping_cart " +
                        "JOIN products ON shopping_cart.product_id = products.product_id " +
                        "WHERE shopping_cart.user_id = ?";

        ShoppingCart cart = new ShoppingCart();
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("DAO: Found product in cart, productId = " + rs.getInt("product_id"));
                // ... rest of your code ...
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
//                BigDecimal discountPercent = rs.getBigDecimal("discount_percent");
//                if (discountPercent == null) discountPercent = BigDecimal.ZERO;
                Product product = new Product(
                        productId,
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getString("color"),
                        rs.getInt("stock"),
                        rs.getBoolean("featured"),
                        rs.getString("image_url")
                );

                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(quantity);
//                item.setDiscountPercent(discountPercent);

                cart.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DAO: SQL Exception message: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return cart;
    }

    @Override
    public void addProductToCart(int userId, int productId) {
        String check = "SELECT quantity FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, 1)";
        String updateSql = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";

        try (Connection conn = dataSource.getConnection()) {
            //check if user already has this product in their cart
            try (PreparedStatement checkStmt = conn.prepareStatement(check)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // If exists, UPDATE
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, userId);
                            updateStmt.setInt(2, productId);
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // If not exists, INSERT
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, productId);
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeProductFromCart(int userId, int productId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

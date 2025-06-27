package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {
    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Order order) {
        String sql = "INSERT INTO orders (user_id, order_date, status) VALUES (?, ?, ?)";

        try (Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setDate(2, Date.valueOf(order.getOrderDate()));
            ps.setString(3, order.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                return rs.getInt(1);
            }
            return 0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLineItem(OrderLineItem item)
    {
        String sql = "INSERT INTO order_line_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPrice());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}


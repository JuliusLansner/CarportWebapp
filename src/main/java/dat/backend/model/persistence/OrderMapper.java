package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper {
    public static ArrayList<Order> orderList(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM ordre";
        ArrayList<Order> orderList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement pre = connection.prepareStatement(sql)) {
                ResultSet rs = pre.executeQuery();

                while (rs.next()) {
                    Order order = new Order(0, 0, 0, 0, 0, null, 0);

                    order.setOrderId(rs.getInt(1));
                    order.setLenght(rs.getInt(2));
                    order.setWidth(rs.getInt(3));
                    order.setTotalPrice(rs.getInt(4));
                    order.setStatus(rs.getInt(5));
                    order.setDate(rs.getTimestamp(6));
                    order.setUserId(rs.getInt(7));

                    orderList.add(order);
                }
            } catch (SQLException ex) {
                throw new DatabaseException(ex, "Something with the sql or the java syntax is wrong");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error logging in. Something went wrong with the database");
        }

        return orderList;
    }


    public static int createOrder(int length, int width, int totalPrice, int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO ordre(længde, brede, samlet_pris, bruger_id) VALUES (?,?,?,?)";
        ResultSet generatedKeys = null;
        int id = 0;

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement pre = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ) {


                pre.setInt(1, length);
                pre.setInt(2, width);
                pre.setInt(3, totalPrice);
                pre.setInt(4, userId);

                pre.executeUpdate();
                generatedKeys = pre.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    generatedKeys.close();
                }

                if (id == 0) {
                    System.out.println("Id not found");
                }


            } catch (SQLException ex) {
                throw new DatabaseException(ex, "Something with the sql or the java syntax is wrong");
            }

        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException(e, "Something went wrong with the database");
        }
        return id;
    }

    public static Order findOrderByUserId(int userId, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM ordre WHERE bruger_id = ?";
        Order order = null;
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt(1);
                int length = rs.getInt(2);
                int width = rs.getInt(3);
                int totalPrice = rs.getInt(4);
                int status = rs.getInt(5);
                Timestamp date = rs.getTimestamp(6);

                order = new Order(orderId, length, width, totalPrice, status, date, userId);
            }
        }

        return order;
    }

    public static Order findOrderByOrderId(int orderId, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM ordre WHERE idordre = ?";
        Order order = null;
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                int length = rs.getInt(2);
                int width = rs.getInt(3);
                int totalPrice = rs.getInt(4);
                int status = rs.getInt(5);
                Timestamp date = rs.getTimestamp(6);
                int userId = rs.getInt(7);

                order = new Order(orderId, length, width, totalPrice, status, date, userId);
            }
        }

        return order;
    }

    public static void updateOrderPrice(int price, int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "UPDATE ordre SET samlet_pris = ? WHERE idordre = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, price);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error updating status for order: " + orderId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating status for order: " + orderId);
        }
    }

    public static void deleteOrder(int id, ConnectionPool connectionPool) throws SQLException {
        String sql = "DELETE FROM ordre WHERE idordre = ?";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
    public static void updateOrderStatus(int status, int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "UPDATE carport.ordre SET status = ? WHERE idordre = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, status);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error updating status for order: " + orderId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating status for order: " + orderId);
        }
    }


}
package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFacade {

    public static ArrayList<Order> orderList() throws DatabaseException {
        return OrderMapper.orderList();
    }

    public static int createOrder(int length, int width, int totalPrice, int userId throws DatabaseException {
        return OrderMapper.createOrder(length, width, totalPrice, userId);
    }

    public static Order findOrderByUserId(int userId) throws SQLException {
        return OrderMapper.findOrderByUserId(userId);
    }

    public static Order findOrderByOrderId(int orderId) throws SQLException {
        return OrderMapper.findOrderByOrderId(orderId);
    }

    public static void updateOrderStatus(int status, int orderId) throws DatabaseException {
        OrderMapper.updateOrderStatus(status, orderId);
    }

    public static void updateOrderPrice(int price, int orderId) throws DatabaseException{
        OrderMapper.updateOrderPrice(price,orderId);
    }
}

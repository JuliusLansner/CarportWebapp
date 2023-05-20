package dat.backend.model.persistence;


import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    static ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    @BeforeEach
    void setUp() throws SQLException {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
    }

    @Test
    void testConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void orderList() throws DatabaseException {
        ArrayList<Order> orderlist = OrderMapper.orderList(connectionPool);

        assertFalse(orderlist.isEmpty());
    }

    @Test
    void createOrder() throws DatabaseException, SQLException {

        int id = OrderMapper.createOrder(100, 100, 200, 47, connectionPool);

        Order order = OrderMapper.findOrderByOrderId(id, connectionPool);

        boolean rightOrder = false;

        if (order.getLenght() == 100 && order.getWidth() == 100 && order.getTotalPrice() == 200 && order.getUserId() == 47 && order.getOrderId() == id) {
            rightOrder = true;
        }

        assertTrue(rightOrder);
    }

    @Test
    void findOrderByUserId() throws SQLException, DatabaseException {
        User user = UserFacade.createUser("orderFindTest5", "123", "orderTest", 1234, 332665311, connectionPool);
        int orderId = OrderMapper.createOrder(1, 1, 1, user.getIdUser(), connectionPool);
        Order order = OrderMapper.findOrderByUserId(user.getIdUser(), connectionPool);

        boolean notnull = false;

        if (order != null) {
            notnull = true;
        }

        assertTrue(notnull);

        OrderMapper.deleteOrder(orderId, connectionPool);
        UserMapper.deleteUser(user.getEmail(), connectionPool);
    }

    @Test
    void findOrderByOrderId() throws SQLException, DatabaseException {
        User user = UserFacade.createUser("orderFindTest5", "123", "orderTest", 1234, 332665311, connectionPool);
        int orderId = OrderMapper.createOrder(1, 1, 1, user.getIdUser(), connectionPool);
        Order order = OrderMapper.findOrderByOrderId(orderId, connectionPool);

        boolean notnull = false;

        if (order != null) {
            notnull = true;
        }

        assertTrue(notnull);

        OrderMapper.deleteOrder(orderId, connectionPool);
        UserMapper.deleteUser(user.getEmail(), connectionPool);
    }

    @Test
    void updateOrderStatus() throws SQLException, DatabaseException {


        int newStatus = 0;
        int orderId = 13;

        Order testOrder = OrderMapper.findOrderByOrderId(orderId, connectionPool);
        testOrder.setStatus(newStatus);

        OrderFacade.updateOrderStatus(newStatus, orderId, connectionPool);

        Order updatedOrderStatus = OrderMapper.findOrderByOrderId(orderId, connectionPool);
        assertEquals(newStatus, updatedOrderStatus.getStatus());

    }

    @Test
    void deleteOrder() throws SQLException, DatabaseException {
        User user = UserFacade.createUser("orderDeleteTest5", "123", "fasan", 1234, 8525636, connectionPool);

        ArrayList<Order> ordersBefore = OrderMapper.orderList(connectionPool);

        int order = OrderMapper.createOrder(1, 1, 1, user.getIdUser(), connectionPool);

        ArrayList<Order> ordersAfter = OrderMapper.orderList(connectionPool);

        boolean orderAfterIncreased = false;
        if (ordersAfter.size() == ordersBefore.size() + 1) {
            orderAfterIncreased = true;
        }
        assertTrue(orderAfterIncreased);

        OrderMapper.deleteOrder(order, connectionPool);
        UserFacade.deleteUser("orderDeleteTest5", connectionPool);
    }
}
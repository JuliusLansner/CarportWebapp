package dat.backend.model.persistence;


import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        boolean notEmpty = false;

        if (orderlist.size() > 0) {
            notEmpty = true;
        }
        assertTrue(notEmpty);
    }

    @Test
    void createOrder() throws DatabaseException, SQLException {

       int id = OrderMapper.createOrder(100, 100, 200, 47, connectionPool);

       Order order = OrderMapper.findOrderByOrderId(id,connectionPool);

       boolean rightOrder = false;

       if(order.getLenght()== 100 && order.getWidth()==100&&order.getTotalPrice()==200&&order.getUserId()==47 && order.getOrderId()==id){
           rightOrder = true;
       }

       assertTrue(rightOrder);
    }

    @Test
    void findOrderByUserId() throws SQLException {
        Order order = OrderMapper.findOrderByUserId(47,connectionPool);
        System.out.println(order);
    }

    @Test
    void findOrderByOrderId() throws SQLException {
        Order order = OrderMapper.findOrderByOrderId(3,connectionPool);
        System.out.println(order);
    }

    @Test
    void updateOrderStatus() throws SQLException, DatabaseException {


        int newStatus = 0;
        int orderId = 13;

        Order testOrder = OrderMapper.findOrderByOrderId(orderId,connectionPool);
        testOrder.setStatus(newStatus);

        OrderFacade.updateOrderStatus(newStatus,orderId,connectionPool);

        Order updatedOrderStatus = OrderMapper.findOrderByOrderId(orderId,connectionPool);
        assertEquals(newStatus, updatedOrderStatus.getStatus());

    }
}
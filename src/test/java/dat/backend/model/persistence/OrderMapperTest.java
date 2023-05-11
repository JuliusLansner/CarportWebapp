package dat.backend.model.persistence;


import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderMapperTest {
    ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    @BeforeEach
    void setUp() throws SQLException {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
    }

    @Test
    void orderList() throws DatabaseException {
        ArrayList<Order> orderlist = OrderMapper.orderList(connectionPool);

        boolean notEmpty = false;

        if (orderlist.size() > 0) {
            notEmpty = true;
        }
        System.out.println(orderlist.get(0).getOrderId());
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
}
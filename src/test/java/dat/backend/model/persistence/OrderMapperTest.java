package dat.backend.model.persistence;


import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    static ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";
    int orderId;
    User user;
    @BeforeEach
    void setUp() throws SQLException, DatabaseException {
        connectionPool = new ConnectionPool(USER,PASSWORD,URL);
        try(Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM bruger");
            statement.execute("DELETE FROM ordre");
        }
        user = UserMapper.createUser("Testuser","123","testroad",5676,67564534,connectionPool);
        orderId = OrderFacade.createOrder(1,1,1,user.getIdUser(),connectionPool);
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
    void findOrder() throws DatabaseException, SQLException {

        Order order = OrderMapper.findOrderByOrderId(orderId, connectionPool);

        boolean rightOrder = false;

        if (order.getLenght() == 1 && order.getWidth() == 1 && order.getTotalPrice() == 1 && order.getUserId() == user.getIdUser() && order.getOrderId() == orderId) {
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
    void createOrder() throws SQLException, DatabaseException {

        ArrayList<Order> ordersBefore = OrderMapper.orderList(connectionPool);

        int order = OrderMapper.createOrder(1, 1, 1, user.getIdUser(), connectionPool);

        ArrayList<Order> ordersAfter = OrderMapper.orderList(connectionPool);

        boolean orderAfterIncreased = false;
        if (ordersAfter.size() == ordersBefore.size() + 1) {
            orderAfterIncreased = true;
        }
        assertTrue(orderAfterIncreased);

    }

    @Test
    void updateOrderPrice() throws DatabaseException, SQLException {
        OrderMapper.updateOrderPrice(200,orderId,connectionPool);
        Order order = OrderMapper.findOrderByOrderId(orderId,connectionPool);
        assertEquals(200,order.getTotalPrice());
    }

    @Test
    void deleteOrder() throws DatabaseException, SQLException {
       ArrayList<Order> orderlist = OrderMapper.orderList(connectionPool);
       OrderMapper.deleteOrder(orderId,connectionPool);
       ArrayList<Order> orderlistAfter = OrderMapper.orderList(connectionPool);

       boolean decreasedByOne = false;

       if(orderlist.size()-1 == orderlistAfter.size()){
           decreasedByOne = true;
       }

       assertTrue(decreasedByOne);
    }
}
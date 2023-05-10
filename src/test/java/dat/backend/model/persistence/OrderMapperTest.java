package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

class OrderMapperTest {
    ConnectionPool connectionPool;
    @BeforeEach
    void setUp() throws SQLException {
        connectionPool = new ConnectionPool();
    }

    @Test
    void orderList() throws DatabaseException {
        ArrayList<Order> bomlist = OrderMapper.orderList(connectionPool);

        System.out.println(bomlist);
    }

    @Test
    void createOrder() throws DatabaseException {

        OrderMapper.createOrder(100,100,200,47,connectionPool);
    }
}
package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;

import java.util.ArrayList;

public class OrderFacade {

    static ArrayList<Order> orderList(ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.orderList(connectionPool);
    }

    static int createOrder(int length, int width, int totalPrice, int userId,  ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(length, width, totalPrice, userId, connectionPool);
    }
}
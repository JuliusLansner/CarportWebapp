package dat.backend.model.persistence;

import dat.backend.model.entities.Bom;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BomMapperTest {
    ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    int orderId;

    @BeforeEach
    void setUp() throws SQLException, DatabaseException {
        connectionPool = new ConnectionPool(USER,PASSWORD,URL);
        try(Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM stykliste");
            statement.execute("DELETE FROM bruger");
            statement.execute("DELETE FROM ordre");
        }
        User user = UserMapper.createUser("Testuser","123","testroad",5676,67564534,connectionPool);
        orderId = OrderFacade.createOrder(1,1,1,user.getIdUser(),connectionPool);
        BomMapper.makeBom(1,orderId,connectionPool);
    }

    @Test
    void getBoms() throws SQLException, DatabaseException {
        ArrayList<Bom> bomlist = BomMapper.getBoms(connectionPool);

        boolean notEmpty = false;

        if (bomlist.size() > 0) {
            notEmpty = true;
        }

        assertTrue(notEmpty);
    }

    @Test
    void makeBom() throws SQLException, DatabaseException {
        ArrayList<Bom> bomlist = BomMapper.getBoms(connectionPool);

        Bom bom = BomMapper.makeBom(200, orderId, connectionPool);

        ArrayList<Bom> newBomlist = BomMapper.getBoms(connectionPool);

        boolean bomAdded = false;

        if (newBomlist.size() > bomlist.size()) {
            bomAdded = true;
        }

        assertTrue(bomAdded);
    }
}
package dat.backend.model.persistence;

import dat.backend.model.entities.Bom;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public class BomFacade {

    public static ArrayList<Bom> getBoms(ConnectionPool connectionPool) throws SQLException {
       return BomMapper.getBoms(connectionPool);
    }

    public static Bom makeBom(int price, int orderId, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        return BomMapper.makeBom(price,orderId,connectionPool);
    }
}

package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialFacade {
    public static ArrayList<Material> materialList() throws DatabaseException {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            connectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return MaterialMapper.materialList(connectionPool);
    }
}

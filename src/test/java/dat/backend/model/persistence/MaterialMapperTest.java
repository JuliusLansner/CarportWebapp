package dat.backend.model.persistence;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaterialMapperTest {
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    private static ConnectionPool connectionPool;



    @Test
    void materialList() throws DatabaseException {
        Material expectedMaterial = new Material(1,"træ","m",100);
        ArrayList<Material> actualMaterial = MaterialFacade.materialList();
        assertEquals(expectedMaterial,actualMaterial);
    }
    @Test
    void testConnection() throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null)
        {
            connection.close();
        }
    }
}
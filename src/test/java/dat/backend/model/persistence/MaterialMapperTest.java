package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaterialMapperTest {
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
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
    void materialList(ConnectionPool connectionPool) throws DatabaseException {
        Material expectedMaterial = new Material(1,"træ","m",100);
        ArrayList<Material> actualMaterial = MaterialFacade.materialList(connectionPool);
        assertEquals(expectedMaterial,actualMaterial);
    }

    @Test
    void updateMaterialPricePrUnit() throws DatabaseException {
        int updatedPricePrUnit = 200;
        int materialId = 1;

        MaterialFacade.updateMaterialPricePrUnit(updatedPricePrUnit,materialId,connectionPool);

        Material updatedMaterial = MaterialFacade.materialList(connectionPool).get(0);
        assertEquals(updatedPricePrUnit, updatedMaterial.getPricePerUnit());

    }
}
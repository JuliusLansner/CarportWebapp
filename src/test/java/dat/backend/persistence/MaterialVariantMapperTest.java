package dat.backend.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialVariantMapperTest {

    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("CREATE DATABASE  IF NOT EXISTS carport_test;");
                stmt.execute("CREATE TABLE IF NOT EXISTS carport_test.m_variant LIKE carport.m_variant;");
            }
        }
        catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
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
    void getMaterialVariantByID() throws DatabaseException {
        MaterialVariant materialVariant = MaterialVariantMapper.getMaterialVariantByID(3, connectionPool);
        assertEquals(3, materialVariant.getMaterialeVariantID());
    }

    @Test
    void getAllMaterialVariants() throws DatabaseException {
        List<MaterialVariant> materialVariants = MaterialVariantMapper.getAllMaterialVariants(connectionPool);
        assertEquals(18, materialVariants.size());
    }

    @Test
    void createMaterialVariant() throws DatabaseException {
        MaterialVariant materialVariant = new MaterialVariant(1, 2, 3, 4);
        MaterialVariantMapper.createMaterialVariant(materialVariant, connectionPool);

        assertEquals(1, materialVariant.getMaterialeVariantID());
        assertEquals(2, materialVariant.getMaterialeID());
        assertEquals(3, materialVariant.getLength());
        assertEquals(4, materialVariant.getPartslistID());
    }

    @Test
    void updateMaterialVariant() throws DatabaseException {
        MaterialVariant materialVariant = MaterialVariantMapper.getMaterialVariantByID(3, connectionPool);
        int newLength = 300;
        materialVariant.setLength(newLength);
        MaterialVariantMapper.updateMaterialVariant(materialVariant, connectionPool);
        MaterialVariant updatedMaterialVariant = MaterialVariantMapper.getMaterialVariantByID(3, connectionPool);
        assertEquals(newLength, updatedMaterialVariant.getLength());
    }

    @Test
    void deleteMaterialVariant() throws DatabaseException {
        MaterialVariant materialVariant = new MaterialVariant(12, 0, 100, 1);
        MaterialVariantMapper.createMaterialVariant(materialVariant, connectionPool);

        int testID = materialVariant.getMaterialeVariantID();
        MaterialVariantMapper.deleteMaterialVariant(testID, connectionPool);

        // checks that a DB exception is thrown, and testID is deleted
        assertThrows(DatabaseException.class, () -> MaterialVariantMapper.getMaterialVariantByID(testID, connectionPool));
    }
}


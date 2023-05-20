package dat.backend.persistence;

import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialVariantFacade;
import dat.backend.model.persistence.MaterialVariantMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialVariantMapperTest {

    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER,PASSWORD,URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("CREATE DATABASE  IF NOT EXISTS carport_test;");
                stmt.execute("CREATE TABLE IF NOT EXISTS carport_test.m_variant LIKE carport.m_variant;");
            }
        } catch (SQLException throwables) {
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
         int materialVariantId = MaterialVariantMapper.createMaterialVariant(4,1,75,"tester",1,connectionPool);
         MaterialVariant mv = MaterialVariantMapper.getMaterialVariantByID(materialVariantId,connectionPool);
        System.out.println(mv.getMaterialeVariantID());
        System.out.println(materialVariantId);
        assertEquals(mv.getMaterialeVariantID(),materialVariantId);
    }

    @Test
    void getAllMaterialVariants() throws DatabaseException {
        List<MaterialVariant> materialVariants = MaterialVariantMapper.getAllMaterialVariants(connectionPool);
        boolean notEmpty = false;
        if(materialVariants.size()>0){
            notEmpty = true;
        }

        assertTrue(notEmpty);
    }

    @Test
    void createMaterialVariant() throws DatabaseException {
        MaterialVariantMapper.createMaterialVariant(1,2,3,"test",2,connectionPool);
    }

    @Test
    void updateMaterialVariant() throws DatabaseException {
        MaterialVariant materialVariant = MaterialVariantMapper.getMaterialVariantByID(4, connectionPool);
        int newLength = 300;
        materialVariant.setLength(newLength);
        MaterialVariantMapper.updateMaterialVariant(materialVariant, connectionPool);
        MaterialVariant updatedMaterialVariant = MaterialVariantMapper.getMaterialVariantByID(4, connectionPool);
        assertEquals(newLength, updatedMaterialVariant.getLength());
    }

    @Test
    void deleteMaterialVariant() throws DatabaseException {
        int id = MaterialVariantMapper.createMaterialVariant(1,1,3,"tester",1,connectionPool);


        MaterialVariantMapper.deleteMaterialVariant(id, connectionPool);

        // checks that a DB exception is thrown, and testID is deleted
        assertThrows(DatabaseException.class, () -> MaterialVariantMapper.getMaterialVariantByID(id, connectionPool));
    }
}


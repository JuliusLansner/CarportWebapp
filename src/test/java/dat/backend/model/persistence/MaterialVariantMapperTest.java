package dat.backend.model.persistence;

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
    void getAllMaterialVariants() throws DatabaseException {
        List<MaterialVariant> materialVariants = MaterialVariantMapper.getAllMaterialVariants(connectionPool);
        boolean notEmpty = false;
        if (materialVariants.size() > 0) {
            notEmpty = true;
        }

        assertTrue(notEmpty);
    }

    @Test
    void createMaterialVariant() throws DatabaseException {
        List<MaterialVariant> materialVariants = MaterialVariantMapper.getAllMaterialVariants(connectionPool);
        int mvId = MaterialVariantMapper.createMaterialVariant(4, 2, 75, "test", 2, connectionPool);
        List<MaterialVariant> materialVariantsAfter = MaterialVariantMapper.getAllMaterialVariants(connectionPool);

        boolean sizeIncreased = false;

        if (materialVariants.size() + 1 == materialVariantsAfter.size()) {
            sizeIncreased = true;
        }
        assertTrue(sizeIncreased);

        MaterialVariantMapper.deleteMaterialVariant(mvId, connectionPool);
    }

    @Test
    void updateMaterialVariant() throws DatabaseException {
        int id = MaterialVariantMapper.createMaterialVariant(1, 1, 1, "test", 200, connectionPool);
        MaterialVariant materialVariant = MaterialVariantMapper.getMaterialVariantByID(id, connectionPool);

        int newLength = 300;
        materialVariant.setLength(newLength);

        MaterialVariantMapper.updateMaterialVariant(materialVariant, connectionPool);

        MaterialVariant updatedMaterialVariant = MaterialVariantMapper.getMaterialVariantByID(id, connectionPool);

        assertEquals(newLength, updatedMaterialVariant.getLength());

        MaterialVariantMapper.deleteMaterialVariant(id,connectionPool);
    }

    @Test
    void deleteMaterialVariant() throws DatabaseException {
        int id = MaterialVariantMapper.createMaterialVariant(1, 1, 3, "tester", 1, connectionPool);


        MaterialVariantMapper.deleteMaterialVariant(id, connectionPool);

        // checks that a DB exception is thrown, and testID is deleted
        assertThrows(DatabaseException.class, () -> MaterialVariantMapper.getMaterialVariantByID(id, connectionPool));
    }
}


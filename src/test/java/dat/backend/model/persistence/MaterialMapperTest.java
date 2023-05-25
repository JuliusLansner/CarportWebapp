package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.entities.User;
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
    ConnectionPool connectionPool;


    @BeforeEach
    void setUp() throws SQLException, DatabaseException {
        connectionPool = new ConnectionPool(USER,PASSWORD,URL);

    }

    @Test
    void getMaterialById() {
        ConnectionPool connectionPool = new ConnectionPool();
        Material material = MaterialMapper.getMaterialById(1, connectionPool);
        System.out.println(material.getPricePerUnit());
    }

    @Test
    void materialList() throws DatabaseException {
        ArrayList<Material>materials = MaterialMapper.materialList(connectionPool);
        assertFalse(materials.isEmpty());
    }

    @Test
    void updateMaterialPricePrUnit() throws DatabaseException {

        MaterialMapper.updateMaterialPricePrUnit(100,1,connectionPool);

        Material material = MaterialMapper.getMaterialById(1,connectionPool);

        assertEquals(100,material.getPricePerUnit());
    }
}
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

     ConnectionPool connectionPool;





    @Test
    void materialList() throws DatabaseException {
        Material expectedMaterial = new Material(1,"træ","m",100);
        ArrayList<Material> actualMaterial = MaterialFacade.materialList();
        assertEquals(expectedMaterial,actualMaterial);
    }


    @Test
    void getMaterialById() {
        ConnectionPool connectionPool = new ConnectionPool();
        Material material = MaterialMapper.getMaterialById(2,connectionPool);
        System.out.println(material.getPricePerUnit());
    }
}
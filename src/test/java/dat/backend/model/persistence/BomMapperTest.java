package dat.backend.model.persistence;

import dat.backend.model.entities.Bom;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BomMapperTest {
ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    @BeforeEach
    void setUp() {
        connectionPool = new ConnectionPool();
    }

    @Test
    void getBoms() throws SQLException, DatabaseException {
     ArrayList<Bom> bomlist = BomMapper.getBoms(connectionPool);

        boolean notEmpty = false;

     if(bomlist.size()>0){
         notEmpty = true;
     }

     assertTrue(notEmpty);
    }

    @Test
    void makeBom() throws SQLException, DatabaseException {
        Bom bom = BomMapper.makeBom(200,2,connectionPool);
        System.out.println(bom.getId());
    }
}
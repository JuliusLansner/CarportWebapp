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
        Bom bom = BomMapper.makeBom(200,1,"Er cool",connectionPool);
        System.out.println(bom.getId());
    }
}
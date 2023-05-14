package dat.backend.model.persistence;

import dat.backend.model.entities.MaterialVariant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaterialVariantListMakerTest {
ConnectionPool connectionPool;
    @BeforeAll
    void setup(){
       connectionPool = new ConnectionPool();
    }

    @Test
    void stolpMaker() {
        ArrayList<MaterialVariant>variants = MaterialVariantListMaker.stolpMaker(600,100, connectionPool);

    }

    @Test
    void spærMaker() {
        ArrayList<MaterialVariant>variants = MaterialVariantListMaker.spærMaker(600,100,connectionPool);
    }
}
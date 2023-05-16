package dat.backend.model.services;

import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaterialVariantListMakerTest {
    ConnectionPool connectionPool;

    @BeforeEach
    void setUp() {
        connectionPool = new ConnectionPool();
    }

    @Test
    void carportMaterialList() throws SQLException, DatabaseException {
        MaterialVariantListMaker.carportMaterialList(600, 100, 3, connectionPool);
    }


    @Test
    void stolpVariantMaker() {
        ArrayList<MaterialVariant> variants = MaterialVariantListMaker.stolpVariantMaker(600, 100, connectionPool);

        System.out.println(variants);

    }

    @Test
    void spærVariantMaker() {
        ArrayList<MaterialVariant> variants = MaterialVariantListMaker.spærVariantMaker(600,100,connectionPool);
        for(MaterialVariant mv: variants){
            System.out.println(mv.getPrice());
            System.out.println(mv.getMaterialeID());
            System.out.println(mv.getLength());
        }
    }

    @Test
    void remVariantMaker() {
        ArrayList<MaterialVariant> variants = MaterialVariantListMaker.remVariantMaker(600,100,connectionPool);
        for(MaterialVariant mv: variants){
            System.out.println(mv.getPrice());
            System.out.println(mv.getMaterialeID());
            System.out.println(mv.getLength());
        }
    }
}
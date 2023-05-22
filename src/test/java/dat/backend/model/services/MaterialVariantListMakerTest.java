package dat.backend.model.services;

import dat.backend.model.entities.Bom;
import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialVariantMapper;
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
    void carportMaterialList240x240() throws SQLException, DatabaseException {
        Bom bom = MaterialVariantListMaker.carportMaterialList(240, 240, 60, connectionPool);

        ArrayList<MaterialVariant> variantList = MaterialVariantMapper.getMaterialVariantListByID(bom.getId(), connectionPool);

        int stolpsLength = 0;
        int stolpsWidth = 0;
        int rems = 0;
        int spær = 0;

        for (MaterialVariant mv : variantList) {
            String description = mv.getDescription();

            if (description.equals("Stolper til længde")) {
                stolpsLength += 1;
            } else if (description.equals("Stolper til brede")) {
                stolpsWidth += 1;
            } else if (description.equals("Spær til taget")) {
                spær += 1;
            } else if (description.equals("Rem til oven på stolper")) {
                rems += 1;
            }
        }

        assertEquals(4, stolpsLength);
        assertEquals(0, stolpsWidth);
        assertEquals(3, spær);
        assertEquals(4, rems);

    }

    @Test
    void carportMaterialList600x600() throws SQLException, DatabaseException {
        Bom bom = MaterialVariantListMaker.carportMaterialList(600, 600, 60, connectionPool);

        ArrayList<MaterialVariant> variantList = MaterialVariantMapper.getMaterialVariantListByID(bom.getId(), connectionPool);

        int stolpsLength = 0;
        int stolpsWidth = 0;
        int rems = 0;
        int spær = 0;

        for (MaterialVariant mv : variantList) {
            String description = mv.getDescription();

            if (description.equals("Stolper til længde")) {
                stolpsLength += 1;
            } else if (description.equals("Stolper til brede")) {
                stolpsWidth += 1;
            } else if (description.equals("Spær til taget")) {
                spær += 1;
            } else if (description.equals("Rem til oven på stolper")) {
                rems += 1;
            }
        }

        assertEquals(6, stolpsLength);
        assertEquals(1, stolpsWidth);
        assertEquals(6, spær);
        assertEquals(4, rems);

    }

    @Test
    void carportMaterialListr400x400() throws SQLException, DatabaseException {
        Bom bom = MaterialVariantListMaker.carportMaterialList(400, 400, 60, connectionPool);

        ArrayList<MaterialVariant> variantList = MaterialVariantMapper.getMaterialVariantListByID(bom.getId(), connectionPool);

        int stolpsLength = 0;
        int stolpsWidth = 0;
        int rems = 0;
        int spær = 0;

        for (MaterialVariant mv : variantList) {
            String description = mv.getDescription();

            if (description.equals("Stolper til længde")) {
                stolpsLength += 1;
            } else if (description.equals("Stolper til brede")) {
                stolpsWidth += 1;
            } else if (description.equals("Spær til taget")) {
                spær += 1;
            } else if (description.equals("Rem til oven på stolper")) {
                rems += 1;
            }
        }

        assertEquals(6, stolpsLength);
        assertEquals(1, stolpsWidth);
        assertEquals(4, spær);
        assertEquals(4, rems);

    }

}
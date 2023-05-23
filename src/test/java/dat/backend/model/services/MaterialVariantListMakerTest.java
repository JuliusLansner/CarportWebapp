package dat.backend.model.services;

import dat.backend.model.entities.Bom;
import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialVariantMapper;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaterialVariantListMakerTest {
    ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    private User user;
    private int orderId;

    @BeforeEach
    void setUp() throws SQLException, DatabaseException {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
        try (Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM m_variant");
            statement.execute("DELETE FROM stykliste");
            statement.execute("DELETE FROM ordre");
            statement.execute("DELETE FROM bruger");

        }

        user = UserFacade.createUser("testingdude", "123", "Homeway", 4555, 65764354, connectionPool);
        orderId = OrderFacade.createOrder(1, 1, 1, user.getIdUser(), connectionPool);
    }

    @Test
    void carportMaterialList240x240() throws SQLException, DatabaseException {


        Bom bom = MaterialVariantListMaker.carportMaterialList(240, 240, orderId, connectionPool);

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
        Bom bom = MaterialVariantListMaker.carportMaterialList(600, 600, orderId, connectionPool);

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
        assertEquals(4, stolpsWidth);
        assertEquals(6, spær);
        assertEquals(4, rems);

    }

    @Test
    void carportMaterialListr400x400() throws SQLException, DatabaseException {
        Bom bom = MaterialVariantListMaker.carportMaterialList(400, 400, orderId, connectionPool);

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
        assertEquals(4, stolpsWidth);
        assertEquals(4, spær);
        assertEquals(4, rems);

    }

}
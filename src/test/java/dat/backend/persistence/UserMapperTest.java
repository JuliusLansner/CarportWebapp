package dat.backend.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("CREATE DATABASE  IF NOT EXISTS carport_test;");
                stmt.execute("CREATE TABLE IF NOT EXISTS carport_test.bruger LIKE carport.bruger;");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("delete from bruger");
                stmt.execute("insert into bruger (email, password, adresse, postnr_idpostnr, telefon, rolle) " + "values ('user@gmail.com','1234','userroad', 1234, 12345678, 'user'),('admin@gmail.com','1234','adminroad', 1234, 11111111, 'admin')");
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
    void login() throws DatabaseException {
        User expectedUser = new User("user@gmail.com", "1234", "userroad", 1234, 87654321);
        User actualUser = UserFacade.login("user@gmail.com", "1234", connectionPool);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void invalidPasswordLogin() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> UserFacade.login("user5@gmail.com", "123", connectionPool));
    }

    @Test
    void invalidUserNameLogin() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> UserFacade.login("bob", "1234", connectionPool));
    }

    @Test
    void deleteUser() throws DatabaseException, SQLException {
        UserFacade.createUser("test@gmail.com", "test", "testroad", 1234, 66665555, connectionPool);
        UserFacade.deleteUser("test@gmail.com", connectionPool);
        assertThrows(DatabaseException.class, () -> UserFacade.login("test@gmail.com", "test", connectionPool));
    }

    @Test
    void createUser() throws SQLException, DatabaseException {
        User user1 = UserMapper.createUser("maldefm@gmail.com","123","klostervej",1,27704845,connectionPool);
        User user2 = UserMapper.findUserByEmail("maldefm@gmail.com",connectionPool);
        assertEquals(user1.getEmail(),user2.getEmail());
    }

    @Test
    void deleteMyAccount() throws SQLException {
        UserFacade.deleteMyAccount("Test@gmail.com","123",connectionPool);
    }
}
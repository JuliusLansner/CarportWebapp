package dat.backend.model.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";

    @BeforeEach
    void setUp() throws SQLException {
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
    void login() throws SQLException, DatabaseException {
    User testUser = UserMapper.createUser("Tester1","123","Tester1",1,1765457,connectionPool);
    User user = UserMapper.login("Tester1","123",connectionPool);
        System.out.println(user.getEmail());
        System.out.println(testUser.getEmail());
    assertEquals(user.getEmail(),testUser.getEmail());

    UserMapper.deleteMyAccount("Tester1","123",connectionPool);
    }

    @Test
    void createUser() throws SQLException, DatabaseException {
        User user1 = UserMapper.createUser("maldefm@gmail.com","123","klostervej",1,27704845,connectionPool);
        User user2 = UserMapper.findUserByEmail("maldefm@gmail.com",connectionPool);

        assertEquals(user1.getEmail(),user2.getEmail());

       UserMapper.deleteMyAccount("maldefm@gmail.com","123",connectionPool);
    }

    @Test
    void findUserByEmail() throws SQLException, DatabaseException {
        User testUser = UserMapper.createUser("Test@gmail.com","123","Testervej",1,1111111,connectionPool);
        User user = UserMapper.findUserByEmail("Test@gmail.com",connectionPool);
        System.out.println(user.getEmail());

        assertEquals(testUser.getEmail(),user.getEmail());

        UserMapper.deleteMyAccount("Test@gmail.com","123",connectionPool);
    }

    @Test
    void deleteMyAccount() throws SQLException {
        UserMapper.deleteMyAccount("Test@gmail.com","123",connectionPool);
    }
}
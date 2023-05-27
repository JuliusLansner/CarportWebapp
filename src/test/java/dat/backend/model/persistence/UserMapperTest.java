package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private ConnectionPool connectionPool;
    private final static String USER = "dev";
    private final static String PASSWORD = "3r!DE32*/fDe";
    private final static String URL = "jdbc:mysql://64.226.126.239:3306/carport_test";
    User user;

    @BeforeEach
    void setUp() throws SQLException, DatabaseException {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
        try (Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM bruger");
        }

        user = UserFacade.createUser("testingdude", "123", "Homeway", 4555, 65764354, connectionPool);
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
    User testuser = UserMapper.login(user.getEmail(), user.getPassword(), connectionPool);
    assertEquals(user,testuser);
    }

    @Test
    void loginNonExistingUser() throws SQLException, DatabaseException {
        assertThrows(DatabaseException.class, () -> {
            UserMapper.login("test", "123", connectionPool);
        });
    }

    @Test
    void createUser() throws SQLException, DatabaseException {
        User user1 = UserMapper.createUser("maldefm@gmail.com","123","klostervej",1,27704845,connectionPool);
        User user2 = UserMapper.findUserByEmail("maldefm@gmail.com",connectionPool);

        assertEquals(user1.getEmail(),user2.getEmail());
    }

    @Test
    void findUserByEmail() throws SQLException, DatabaseException {

        User testuser = UserMapper.findUserByEmail(user.getEmail(),connectionPool);

        assertEquals(user.getEmail(),testuser.getEmail());
    }

    @Test
    void deleteMyAccount() throws SQLException, DatabaseException {
        List<User>users = UserMapper.allUsers(connectionPool);
        UserMapper.deleteUser(user.getEmail(),connectionPool);
        List<User> usersAfter = UserMapper.allUsers(connectionPool);

        boolean addedUser = false;

        if(users.size()-1 == usersAfter.size()){
            addedUser = true;
        }
        assertTrue(addedUser);
    }

    @Test
    void allUsers() throws DatabaseException {
        List<User> userList = UserMapper.allUsers(connectionPool);
        assertNotNull(userList);
        assertFalse(userList.isEmpty());
        // the test db contains 1 user at the time of testing
        assertEquals(1, userList.size());
    }
}
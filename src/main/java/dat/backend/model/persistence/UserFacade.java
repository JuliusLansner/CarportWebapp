package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public class UserFacade {
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        return UserMapper.login(email, password, connectionPool);
    }

    public static User createUser(String email, String password, String adress, int zipCode, int phoneNumber, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        return UserMapper.createUser(email, password, adress, zipCode, phoneNumber, connectionPool);
    }

    public static void deleteUser(String email, ConnectionPool connectionPool) throws DatabaseException {
        UserMapper.deleteUser(email, connectionPool);
    }

    public static User findUserByEmail(String email, ConnectionPool connectionPool) throws SQLException {
        return UserMapper.findUserByEmail(email,connectionPool);
    }

    public static void deleteMyAccount(String email, String password, ConnectionPool connectionPool) throws SQLException {
        UserMapper.deleteMyAccount(email,password,connectionPool);
    }

    public static List<User> allUsers(ConnectionPool connectionPool) throws DatabaseException {
        return UserMapper.allUsers(connectionPool);
    }
}

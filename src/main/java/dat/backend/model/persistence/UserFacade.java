package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;

public class UserFacade
{
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        return UserMapper.login(email, password, connectionPool);
    }

    public static User createUser(String email, String password, String address, int zip, int phone, String role, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        return UserMapper.createUser(email, password, address, zip, phone, connectionPool);
    }

    public static User findUserByEmail(String email, ConnectionPool connectionPool) throws SQLException {
        return UserMapper.findUserByEmail(email,connectionPool);
    }

    public static void deleteMyAccount(String email, String password, ConnectionPool connectionPool) throws SQLException {
        UserMapper.deleteMyAccount(email,password,connectionPool);
    }
}

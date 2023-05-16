package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper {

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        User user;

        String sql = "SELECT * FROM bruger WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String address = rs.getString("adresse");
                    int zipCode = rs.getInt("postnr_idpostnr");
                    int phoneNumber = rs.getInt("telefon");
                    String role = rs.getString("rolle");

                    user = new User(email, password, address, zipCode, phoneNumber);
                } else {
                    throw new DatabaseException("Wrong username or password");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    public static User createUser(String email, String password, String address, int zip, int phone, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        String sql = "INSERT INTO bruger (email, password, adresse, postnr_idpostnr, telefon) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, address);
            statement.setInt(4, zip);
            statement.setInt(5, phone);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Failed to insert user with email " + email + " into the database");
            }

            User user = new User(email, password, address, zip, phone);

            return user;
        }
    }

    public static void deleteUser(String email, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "DELETE FROM bruger WHERE email = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Error deleting user with email :" + email);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error deleting user with email :" + email);
        }
    }

    public static User findUserByEmail(String email, ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM bruger WHERE email = ?";
        User user = null;
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("idbruger");
                String password = rs.getString("password");
                String address = rs.getString("adresse");
                int zip = rs.getInt("postnr_idpostnr");
                int phone = rs.getInt("telefon");
                String role = rs.getString("rolle");
                user = new User(userId, email, password, address, zip, phone, role);
            }
        }
        return user;
    }

    public static void deleteMyAccount(String email, String password, ConnectionPool connectionPool) throws SQLException {
        String sql = "DELETE FROM bruger WHERE email = ? and password = ?";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeUpdate();
        }
    }
}

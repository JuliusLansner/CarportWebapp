package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialMapper {
    /* This mapper picks up all the info about a specific material we've put in to the databse
*/
    static ArrayList<Material> materialList(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM material";
        ArrayList<Material> materialList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement pre = connection.prepareStatement(sql)) {
                ResultSet rs = pre.executeQuery();

                while (rs.next()) {
                    Material material = new Material(0, "","",0);

                    material.setIdMateriale(rs.getInt(1));
                    material.setBeskrivelse(rs.getString(2));
                    material.setEnhed(rs.getString(3));
                    material.setPrisprenhed(rs.getInt(4));

                    materialList.add(material);
                }
            } catch (SQLException ex) {
                throw new DatabaseException(ex, "Error. Something went wrong. Either with the SQL, or the java syntax.");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error. Something went wrong with the login");
        }

        return materialList;
    }
}


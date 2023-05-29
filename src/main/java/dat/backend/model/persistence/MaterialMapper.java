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
    public static ArrayList<Material> materialList() throws DatabaseException {
        String sql = "SELECT * FROM materiale";
        ArrayList<Material> materialList = new ArrayList<>();
        ConnectionPool connectionPool = new ConnectionPool();
        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement pre = connection.prepareStatement(sql)) {
                ResultSet rs = pre.executeQuery();

                while (rs.next()) {
                    Material material = new Material(0, "", "", 0);

                    material.setIdMaterial(rs.getInt(1));
                    material.setDescription(rs.getString(2));
                    material.setUnit(rs.getString(3));
                    material.setPricePerUnit(rs.getInt(4));

                    materialList.add(material);

                }
                connectionPool.close();
            } catch (SQLException ex) {
                throw new DatabaseException(ex, "Error. Something went wrong. Either with the SQL, or the java syntax.");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error. Something went wrong with the login");
        }

        return materialList;
    }

    public static Material getMaterialById(int id){
        String sql = "SELECT * FROM materiale WHERE idmateriale = ?";
        Material material = null;
        ConnectionPool connectionPool = new ConnectionPool();
        try(Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String beskrivelse = resultSet.getString("beskrivelse");
                String enhed = resultSet.getString("enhed");
                int pris = resultSet.getInt("pris_per_enhed");

                material = new Material(beskrivelse, enhed, pris);
            }
            connectionPool.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return material;
    }

    public static void updateMaterialPricePrUnit(int updatedPricePrUnit, int materialId) throws DatabaseException {

        String sql = "UPDATE carport.materiale SET pris_per_enhed = ? WHERE materiale.idmateriale = ?";
        ConnectionPool connectionPool = new ConnectionPool();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, updatedPricePrUnit);
            ps.setInt(2, materialId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error updating material price");
            }
            connectionPool.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating material price");
        }
    }
}
package dat.backend.model.persistence;

import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialVariantMapper {

    static public MaterialVariant getMaterialVariantByID(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {

        MaterialVariant materialVariant;

        String sql = "SELECT * FROM m_variant WHERE idm_variant = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, IDMaterialVariant);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int materialeID = rs.getInt("materiale_id");
                    int length = rs.getInt("længde");
                    int partslistID = rs.getInt("stykliste_idstykliste");
                    materialVariant = new MaterialVariant(IDMaterialVariant, materialeID, length, partslistID);
                } else {
                    throw new DatabaseException("No material variant were found with: " + IDMaterialVariant);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error fetching materialeVariant with ID = " + IDMaterialVariant);
        }
        return materialVariant;
    }

    static public List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {

        List<MaterialVariant> materialVariants = new ArrayList<>();

        String sql = "SELECT * FROM m_variant";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int IDMaterialVariant = rs.getInt("idm_variant");
                    int materialeID = rs.getInt("materiale_id");
                    int length = rs.getInt("længde");
                    int partslistID = rs.getInt("stykliste_idstykliste");
                    materialVariants.add(new MaterialVariant(IDMaterialVariant, materialeID, length, partslistID));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error fetching all material variants");
        }
        return materialVariants;
    }

    static public void createMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO m_variant (materiale_id, længde, stykliste_idstykliste) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, materialVariant.getMaterialeID());
                ps.setInt(2, materialVariant.getLength());
                ps.setInt(3, materialVariant.getPartslistID());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Error creating material variant");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating material variant");
        }
    }

    static public void updateMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "UPDATE m_variant SET materiale_id = ?, længde = ?, stykliste_idstykliste = ? WHERE idm_variant = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, materialVariant.getMaterialeID());
                ps.setInt(2, materialVariant.getLength());
                ps.setInt(3, materialVariant.getPartslistID());
                ps.setInt(4, materialVariant.getMaterialeVariantID());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Error updating material variant: " + materialVariant.getMaterialeVariantID());
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating material variant: " + materialVariant.getMaterialeVariantID());
        }
    }

    static public void deleteMaterialVariant(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "DELETE FROM m_variant WHERE idm_variant = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, IDMaterialVariant);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Error deleting material variant with ID: : " + IDMaterialVariant);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error deleting material variant with ID: : " + IDMaterialVariant);
        }
    }
}

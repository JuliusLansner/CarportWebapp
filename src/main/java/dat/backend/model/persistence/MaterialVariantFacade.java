package dat.backend.model.persistence;

import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

public class MaterialVariantFacade {

    public static MaterialVariant getMaterialVariantByID(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getMaterialVariantByID(IDMaterialVariant, connectionPool);
    }

    public static List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getAllMaterialVariants(connectionPool);
    }

    public static void createMaterialVariant(int materialId, int length, int bonId, String description, int price, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.createMaterialVariant(materialId, length, bonId, description, price, connectionPool);
    }

    public static void updateMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.updateMaterialVariant(materialVariant, connectionPool);
    }

    public static void deleteMaterialVariant(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.deleteMaterialVariant(IDMaterialVariant, connectionPool);
    }
}




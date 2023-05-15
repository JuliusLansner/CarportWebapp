package dat.backend.model.persistence;

import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

public class MaterialVariantFacade {

    static public MaterialVariant getMaterialVariantByID(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getMaterialVariantByID(IDMaterialVariant, connectionPool);
    }

    static public List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getAllMaterialVariants(connectionPool);
    }

    static public void createMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.createMaterialVariant(materialVariant, connectionPool);
    }

    static public void updateMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.updateMaterialVariant(materialVariant, connectionPool);
    }

    static public void deleteMaterialVariant(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.deleteMaterialVariant(IDMaterialVariant, connectionPool);
    }
}




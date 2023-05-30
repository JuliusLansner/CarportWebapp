package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialFacade {
    public static ArrayList<Material> materialList(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.materialList(connectionPool);
    }

    public static void  updateMaterialPricePrUnit(int updatedPricePrUnit, int materialId , ConnectionPool connectionPool) throws DatabaseException {
        MaterialMapper.updateMaterialPricePrUnit(updatedPricePrUnit, materialId, connectionPool);
    }
    public static Material getMaterialById(int id, ConnectionPool connectionPool){
        return MaterialMapper.getMaterialById(id,connectionPool);
    }
 }

package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialFacade {
    public static ArrayList<Material> materialList() throws DatabaseException {
        return MaterialMapper.materialList();
    }

    public static void  updateMaterialPricePrUnit(int updatedPricePrUnit, int materialId) throws DatabaseException {
        MaterialMapper.updateMaterialPricePrUnit(updatedPricePrUnit, materialId);
    }
    public static Material getMaterialById(int id){
        return MaterialMapper.getMaterialById(id);
    }
 }

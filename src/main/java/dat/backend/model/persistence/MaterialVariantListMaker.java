package dat.backend.model.persistence;

import dat.backend.model.entities.Bom;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialVariantListMaker {

    /**
     * @param lengthInCm     length of the carport
     * @param widthInCm      width of the carport
     * @param orderId
     * @param connectionPool
     * @throws SQLException
     * @throws DatabaseException
     */
    public void carportMaterialList(int lengthInCm, int widthInCm, int orderId, ConnectionPool connectionPool) throws SQLException, DatabaseException {

        //makes material variants of stolps based on carport height and width and puts into list named stolps.
        ArrayList<MaterialVariant> stolps = stolpVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates price of all the stolps
        int priceOfStolps = calculatePriceOfVariantList(stolps);

        ArrayList<MaterialVariant> spær = spærVariantMaker(lengthInCm, widthInCm, connectionPool);
        int priceOfSpær = calculatePriceOfVariantList(spær);

        ArrayList<MaterialVariant> rems = remVariantMaker(lengthInCm, widthInCm, connectionPool);
        int priceOfRems = calculatePriceOfVariantList(rems);

        //calculates total price of all material variants
        int totalPrice = priceOfSpær + priceOfStolps + priceOfRems;

        //adds a list to the DB for material variants and puts it into an objeckt named bom of type Bom
        Bom bom = BomFacade.makeBom(totalPrice, orderId, connectionPool);

        //adds all the stolp variants to the DB
        for (MaterialVariant mv : stolps) {
            String description = "Stolper til at putte i jorden";
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), description, mv.getPrice(), connectionPool);
        }

        //adds all the spær variants to the DB
        for (MaterialVariant mv : spær) {
            String description = "Spær til taget";
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), description, mv.getPrice(), connectionPool);
        }

    }

    private static int calculatePriceOfVariantList(ArrayList<MaterialVariant> variants) {
        int totalPrice = 0;

        for (MaterialVariant material : variants) {
            totalPrice += material.getPrice();
        }
        return totalPrice;
    }


    /**
     * @param lengthInCm     length of the carport
     * @param widthInCm      width of the carport
     * @param connectionPool
     * @return returns a list of created stolp variants that are based on the carport length and width
     */
    private static ArrayList<MaterialVariant> stolpVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> stolps = new ArrayList<>();
        //sets measurement for stolp
        double maxDistStolpsInCm = 310;
        double widthOfStolp = 97;
        int heigtOfStolp = 240;

        //calculates the amount of stolps needed based on the length
        double amountOfStolps = Math.ceil(lengthInCm / (maxDistStolpsInCm + 97)) * 2;
        System.out.println(amountOfStolps);

        //creates the amount of stolps needed and adds to the list
        for (int i = 0; i < amountOfStolps; i++) {
            int price = variantPriceCalculater(lengthInCm, 2, connectionPool);
            MaterialVariant stolpVariant = new MaterialVariant(2, heigtOfStolp, price);
            stolps.add(stolpVariant);
        }
        return stolps;
    }

    private static ArrayList<MaterialVariant> remVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> rems = new ArrayList<>();

        double widthOfRem = 45;

        for (int i = 0; i < 4; i++) {
            int price = variantPriceCalculater(lengthInCm, 1, connectionPool);
            MaterialVariant materialVariant = new MaterialVariant(lengthInCm, 1, price);
            rems.add(materialVariant);
        }
        return rems;
    }

    /**
     * @param lengthInCm     length of the carport
     * @param widthInCm      width of the carport
     * @param connectionPool
     * @return returns a list of created spær variants based on carport length and width
     */
    private static ArrayList<MaterialVariant> spærVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> spær = new ArrayList<>();

        double maxDistSpærInCm = 60;
        double widthOfSpær = 45;

        double amountOfSpær = Math.ceil(lengthInCm / (maxDistSpærInCm + widthOfSpær));
        System.out.println(amountOfSpær);
        for (int i = 0; i < amountOfSpær; i++) {
            //calculates price of material variant
            int price = variantPriceCalculater(widthInCm, 1, connectionPool);
            MaterialVariant spærVariant = new MaterialVariant(1, widthInCm, price);
            spær.add(spærVariant);
        }
        return spær;
    }


    private static int variantPriceCalculater(int lengthInCm, int materialId, ConnectionPool connectionPool) {
        int lengthInM = lengthInCm / 100;
        //finds the material with the id
        Material material = MaterialFacade.getMaterialById(materialId, connectionPool);
        //gets the price per unit of the material
        int pricePerUnit = material.getPricePerUnit();
        //calculates the price based on material length
        return lengthInM * pricePerUnit;
    }

}

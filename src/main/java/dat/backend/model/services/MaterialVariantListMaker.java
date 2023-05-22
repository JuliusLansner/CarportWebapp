package dat.backend.model.services;

import dat.backend.model.entities.Bom;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.MaterialVariant;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.BomFacade;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialFacade;
import dat.backend.model.persistence.MaterialVariantFacade;

import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialVariantListMaker {

    /**
     * @param lengthInCm     length of the carport
     * @param widthInCm      width of the carport
     * @param orderId
     * @param connectionPool
     * @return returns a bill og materials
     * @throws SQLException
     * @throws DatabaseException
     */
    public static Bom carportMaterialList(int lengthInCm, int widthInCm, int orderId, ConnectionPool connectionPool) throws SQLException, DatabaseException {

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
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), mv.getDescription(), mv.getPrice(), connectionPool);
        }

        //adds all the spær variants to the DB
        for (MaterialVariant mv : spær) {
            String description = "Spær til taget";
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), description, mv.getPrice(), connectionPool);
        }

        //adds all the rem variants to the DB
        for (MaterialVariant mv:rems){
            String description = "Rem til oven på stolper";
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), description, mv.getPrice(), connectionPool);
        }

        return bom;
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
        int heigtOfStolp = 300;

        //calculates the amount of stolps needed on the length of the carport
        int amountOfStolpsLength = amountOfstolpsLength(lengthInCm);
        //calculates the amount of stolps needed on the width of the carport
        int amountOfStolpsWidth = amountOfstolpsWidth(widthInCm);


        //creates the amount of stolps needed for length and adds to the list
        for (int i = 0; i < amountOfStolpsLength; i++) {
            int price = variantPriceCalculater(heigtOfStolp, 2, connectionPool);
            String description = "Stolper til længde";
            MaterialVariant stolpVariant = new MaterialVariant(2, heigtOfStolp, price, description);
            stolps.add(stolpVariant);
        }

        //creates the amount of stolps needed for width and adds to the list
        if(amountOfStolpsWidth>0){
            for (int i = 0; i < amountOfStolpsWidth; i++) {
                int price = variantPriceCalculater(heigtOfStolp, 2, connectionPool);
                String description = "Stolper til brede";
                MaterialVariant stolpVariant = new MaterialVariant(2, heigtOfStolp, price, description);
                stolps.add(stolpVariant);
            }
        }


        return stolps;
    }

    private static ArrayList<MaterialVariant> remVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> rems = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            int price = variantPriceCalculater(lengthInCm, 1, connectionPool);
            MaterialVariant materialVariant = new MaterialVariant(1, lengthInCm, price);
            rems.add(materialVariant);
        }

        for (int i = 0; i < 2; i++) {
            int price = variantPriceCalculater(widthInCm, 1, connectionPool);
            MaterialVariant materialVariant = new MaterialVariant(1, widthInCm, price);
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

    private static int amountOfstolpsLength(int length){
        int amountOfStolps = 0;

        switch(length){
            case 240:
                amountOfStolps = 4;
                break;
            case 270:
                amountOfStolps = 4;
                break;
            case 400:
                amountOfStolps = 6;
                break;
            case 600:
                amountOfStolps = 6;
                break;
        }
        return amountOfStolps;
    }

    private static int amountOfstolpsWidth(int width){
        int amountOfStolps = 0;

        switch(width){
            case 240:
                amountOfStolps = 0;
                break;
            case 270:
                amountOfStolps = 0;
                break;
            case 400:
                amountOfStolps = 4;
                break;
            case 600:
                amountOfStolps = 4;
                break;
        }
        return amountOfStolps;
    }

}

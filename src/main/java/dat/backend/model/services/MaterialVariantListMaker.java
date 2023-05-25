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
     * Calculates all the materials variants needed for a carport, and the total price of them all, also creates a Bom(bill of materials) and puts all the variants up on the database.
     * @param lengthInCm of the carport
     * @param widthInCm width of the carport
     * @param orderId of the order
     * @param connectionPool pool of connections for database.
     * @return returns a bill of materials
     * @throws SQLException
     * @throws DatabaseException
     */
    public static Bom carportMaterialList(int lengthInCm, int widthInCm, int orderId, ConnectionPool connectionPool) throws SQLException, DatabaseException {

        //makes material variants of stolps based on carport height and width, and puts into list named stolps.
        ArrayList<MaterialVariant> stolps = stolpVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates price of all the stolps and stores it in an integer.
        int priceOfStolps = calculatePriceOfVariantList(stolps);
        //makes material variants of spær based on carport height and width, and puts into list named spær.
        ArrayList<MaterialVariant> spær = spærVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates total price of all the spær and stores it in an integer.
        int priceOfSpær = calculatePriceOfVariantList(spær);
        //makes material variants of rems based on carport height and width, and puts into list named rems.
        ArrayList<MaterialVariant> rems = remVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates price of all the rems and stores it in an integer.
        int priceOfRems = calculatePriceOfVariantList(rems);

        //calculates total price of all the material variants.
        int totalPrice = priceOfSpær + priceOfStolps + priceOfRems;

        //adds a list to the DB for material variants and puts it into an object named bom of type Bom.
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
        for (MaterialVariant mv : rems) {
            String description = "Rem til oven på stolper";
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), description, mv.getPrice(), connectionPool);
        }
        //returns the bill of materials created.
        return bom;
    }

    /**
     * Calculates total price of a list of variants.
     *
     * @param variants list of variants.
     * @return total price.
     */
    private static int calculatePriceOfVariantList(ArrayList<MaterialVariant> variants) {
        int totalPrice = 0;

        for (MaterialVariant material : variants) {
            totalPrice += material.getPrice();
        }
        return totalPrice;
    }


    /**
     * Calculates amount of stolps needed for carport, and also calculates price of each variant.
     *
     * @param lengthInCm     of the carport
     * @param widthInCm      of the carport
     * @param connectionPool pool of connections for database.
     * @return list of created stolp variants that are based on the carport length and width
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
        if (amountOfStolpsWidth > 0) {
            for (int i = 0; i < amountOfStolpsWidth; i++) {
                int price = variantPriceCalculater(heigtOfStolp, 2, connectionPool);
                String description = "Stolper til brede";
                MaterialVariant stolpVariant = new MaterialVariant(2, heigtOfStolp, price, description);
                stolps.add(stolpVariant);
            }
        }


        return stolps;
    }

    /**
     * Calculates measurements and amount of rems needed for carport, and also calculates the price of each variant.
     *
     * @param lengthInCm     of the carport.
     * @param widthInCm      of the carport.
     * @param connectionPool pool of connections for database.
     * @return list of rem variants needed for carport.
     */
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
     * Calculates measurements and amount of spær needed for carport, and also calculates price of each variant.
     *
     * @param lengthInCm     of the carport.
     * @param widthInCm      of the carport.
     * @param connectionPool pool of connections for database.
     * @return list of spær variants based on carport length and width.
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

    /**
     * Calculates the price for a materialvariant.
     *
     * @param lengthInCm     of the materialvariant.
     * @param materialId     of the material the variant is made of.
     * @param connectionPool pool of connections to the database.
     * @return price of the materialvariant.
     */
    private static int variantPriceCalculater(int lengthInCm, int materialId, ConnectionPool connectionPool) {
        int lengthInM = lengthInCm / 100;
        //finds the material with the id
        Material material = MaterialFacade.getMaterialById(materialId, connectionPool);
        //gets the price per unit of the material
        int pricePerUnit = material.getPricePerUnit();
        //calculates the price based on material length
        return lengthInM * pricePerUnit;
    }

    /**
     * Calculates the amount of stolps needed for the length of the carport.
     *
     * @param length of the carport.
     * @return the amount of stolps for the length.
     */
    private static int amountOfstolpsLength(int length) {
        int amountOfStolps = 0;

        switch (length) {
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

    /**
     * Calculates amount of stolps needed for the width of a carport
     *
     * @param width of the carport
     * @return amount of stolps needed for width.
     */
    private static int amountOfstolpsWidth(int width) {
        int amountOfStolps = 0;

        switch (width) {
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

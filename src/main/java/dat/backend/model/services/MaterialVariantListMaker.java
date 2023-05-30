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

        //makes material variants of post based on carport height and width, and puts into list named post.
        ArrayList<MaterialVariant> post = postVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates price of all the post and stores it in an integer.
        int priceOfPost = calculatePriceOfVariantList(post);
        //makes material variants of rafter based on carport height and width, and puts into list named rafter.
        ArrayList<MaterialVariant> rafter = rafterVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates total price of all the rafter and stores it in an integer.
        int priceOfRafter = calculatePriceOfVariantList(rafter);
        //makes material variants of beam based on carport height and width, and puts into list named beam.
        ArrayList<MaterialVariant> beam = beamVariantMaker(lengthInCm, widthInCm, connectionPool);
        //calculates price of all the beam and stores it in an integer.
        int priceOfBeams = calculatePriceOfVariantList(beam);

        //calculates total price of all the material variants.
        int totalPrice = priceOfRafter + priceOfPost + priceOfBeams;

        //adds a list to the DB for material variants and puts it into an object named bom of type Bom.
        Bom bom = BomFacade.makeBom(totalPrice, orderId, connectionPool);

        //adds all the post variants to the DB
        for (MaterialVariant mv : post) {
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), mv.getDescription(), mv.getPrice(), connectionPool);
        }

        //adds all the rafter variants to the DB
        for (MaterialVariant mv : rafter) {
            String description = "Spær til taget";
            MaterialVariantFacade.createMaterialVariant(mv.getMaterialeID(), mv.getLength(), bom.getId(), description, mv.getPrice(), connectionPool);
        }

        //adds all the beams variants to the DB
        for (MaterialVariant mv : beam) {
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
     * Calculates amount of post needed for carport, and also calculates price of each variant.
     *
     * @param lengthInCm     of the carport
     * @param widthInCm      of the carport
     * @param connectionPool pool of connections for database.
     * @return list of created post variants that are based on the carport length and width
     */
    private static ArrayList<MaterialVariant> postVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> post= new ArrayList<>();
        int heigtOfPost = 300;

        //calculates the amount of posts needed on the length of the carport
        int amountOfPostLength = amountOfpostLength(lengthInCm);
        //calculates the amount of posts needed on the width of the carport
        int amountOfPostWidth = amountOfpostWidth(widthInCm);


        //creates the amount of posts needed for length and adds to the list
        for (int i = 0; i < amountOfPostLength; i++) {
            int price = variantPriceCalculater(heigtOfPost, 2, connectionPool);
            String description = "Stolper til længde";
            MaterialVariant postVariant = new MaterialVariant(2, heigtOfPost, price, description);
            post.add(postVariant);
        }

        //creates the amount of posts needed for width and adds to the list
        if (amountOfPostWidth > 0) {
            for (int i = 0; i < amountOfPostWidth; i++) {
                int price = variantPriceCalculater(heigtOfPost, 2, connectionPool);
                String description = "Stolper til brede";
                MaterialVariant stolpVariant = new MaterialVariant(2, heigtOfPost, price, description);
                post.add(stolpVariant);
            }
        }


        return post;
    }

    /**
     * Calculates measurements and amount of rems needed for carport, and also calculates the price of each variant.
     *
     * @param lengthInCm     of the carport.
     * @param widthInCm      of the carport.
     * @param connectionPool pool of connections for database.
     * @return list of rem variants needed for carport.
     */
    private static ArrayList<MaterialVariant> beamVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> beams = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            int price = variantPriceCalculater(lengthInCm, 1, connectionPool);
            MaterialVariant materialVariant = new MaterialVariant(1, lengthInCm, price);
            beams.add(materialVariant);
        }

        for (int i = 0; i < 2; i++) {
            int price = variantPriceCalculater(widthInCm, 1, connectionPool);
            MaterialVariant materialVariant = new MaterialVariant(1, widthInCm, price);
            beams.add(materialVariant);
        }

        return beams;
    }


    /**
     * Calculates measurements and amount of rafter needed for carport, and also calculates price of each variant.
     *
     * @param lengthInCm     of the carport.
     * @param widthInCm      of the carport.
     * @param connectionPool pool of connections for database.
     * @return list of rafter variants based on carport length and width.
     */
    private static ArrayList<MaterialVariant> rafterVariantMaker(int lengthInCm, int widthInCm, ConnectionPool connectionPool) {
        ArrayList<MaterialVariant> rafter = new ArrayList<>();

        double maxDistRafterInCm = 60;
        double widthOfRafter = 45;

        double amountOfRafter = Math.ceil(lengthInCm / (maxDistRafterInCm + widthOfRafter));
        System.out.println(amountOfRafter);
        for (int i = 0; i < amountOfRafter; i++) {
            //calculates price of material variant
            int price = variantPriceCalculater(widthInCm, 1, connectionPool);
            MaterialVariant rafterVariant = new MaterialVariant(1, widthInCm, price);
            rafter.add(rafterVariant);
        }
        return rafter;
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
     * Calculates the amount of posts needed for the length of the carport.
     *
     * @param length of the carport.
     * @return the amount of posts for the length.
     */
    private static int amountOfpostLength(int length) {
        int amountOfPost = 0;

        switch (length) {
            case 240:
                amountOfPost = 4;
                break;
            case 270:
                amountOfPost = 4;
                break;
            case 400:
                amountOfPost = 6;
                break;
            case 600:
                amountOfPost = 6;
                break;
        }
        return amountOfPost;
    }

    /**
     * Calculates amount of posts needed for the width of a carport
     *
     * @param width of the carport
     * @return amount of posts needed for width.
     */
    private static int amountOfpostWidth(int width) {
        int amountOfPost = 0;

        switch (width) {
            case 240:
                amountOfPost = 0;
                break;
            case 270:
                amountOfPost = 0;
                break;
            case 400:
                amountOfPost = 4;
                break;
            case 600:
                amountOfPost = 4;
                break;
        }
        return amountOfPost;
    }

}

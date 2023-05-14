package dat.backend.model.persistence;

import dat.backend.model.entities.Bom;
import dat.backend.model.entities.MaterialVariant;

import java.util.ArrayList;

public class MaterialVariantListMaker {
    public void carportMaterialList(int lengthInCm, int widthInCm){
       stolpMaker(lengthInCm,widthInCm);
       spærMaker(lengthInCm,widthInCm);

    }

    public static ArrayList<MaterialVariant>stolpMaker(int lengthInCm, int widthInCm){
        ArrayList<MaterialVariant>stolps = new ArrayList<>();

        double maxDistStolpsInCm = 310;
        double widthOfStolp = 97;
        int heigtOfStolp = 240;

        double amountOfStolps = Math.ceil(lengthInCm/(maxDistStolpsInCm+97))*2;
        System.out.println(amountOfStolps);

        for (int i = 0; i < amountOfStolps; i++) {
            MaterialVariant stolpVariant = new MaterialVariant(2,heigtOfStolp);
            stolps.add(stolpVariant);
        }
        return stolps;
    }

    public static ArrayList<MaterialVariant>spærMaker(int lengthInCm, int widthInCm){
        ArrayList<MaterialVariant>spær = new ArrayList<>();

        double maxDistSpærInCm = 60;
        double widthOfSpær = 45;

        double amountOfSpær = Math.ceil(lengthInCm/(maxDistSpærInCm+widthOfSpær));
        System.out.println(amountOfSpær);
        for (int i = 0; i < amountOfSpær; i++) {
            MaterialVariant spærVariant = new MaterialVariant(1,widthInCm);
            spær.add(spærVariant);
        }
        return spær;
    }


}

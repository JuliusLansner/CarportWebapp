package dat.backend.model.persistence;

import dat.backend.model.entities.MaterialVariant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaterialVariantListMakerTest {

    @Test
    void stolpMaker() {
        ArrayList<MaterialVariant>variants = MaterialVariantListMaker.stolpMaker(600,100);

    }

    @Test
    void spærMaker() {
        ArrayList<MaterialVariant>variants = MaterialVariantListMaker.spærMaker(600,100);
    }
}
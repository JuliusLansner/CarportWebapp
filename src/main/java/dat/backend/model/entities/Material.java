package dat.backend.model.entities;

public class Material {
    int idMaterial;
    String description;
    String unit;
    int priceperunit;

    public Material(int idMaterial, String description, String unit, int priceperunit) {
        this.idMaterial = idMaterial;
        this.description = description;
        this.unit = unit;
        this.priceperunit = priceperunit;


    }
//sets idMaterial, not sure about this one or if it's necessary.
    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPriceperunit(int priceperunit) {
        this.priceperunit = priceperunit;
    }


    // returns beskrivelse, not sure about this one.
    @Override
    public String toString() {
        return description;
    }
}

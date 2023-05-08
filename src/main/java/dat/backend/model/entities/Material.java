package dat.backend.model.entities;

public class Material {
    int idMateriale;
    String beskrivelse;
    String enhed;
    int prisprenhed;

    public Material(int idMateriale, String beskrivelse, String enhed, int prisprenhed) {
        this.idMateriale = idMateriale;
        this.beskrivelse = beskrivelse;
        this.enhed = enhed;
        this.prisprenhed = prisprenhed;


    }
//sets idMaterial, not sure about this one or if it's necessary.
    public void setIdMateriale(int idMateriale) {
        this.idMateriale = idMateriale;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public void setEnhed(String enhed) {
        this.enhed = enhed;
    }

    public void setPrisprenhed(int pris) {
        this.prisprenhed = pris;
    }


    // returns beskrivelse, not sure about this one.
    @Override
    public String toString() {
        return beskrivelse;
    }
}

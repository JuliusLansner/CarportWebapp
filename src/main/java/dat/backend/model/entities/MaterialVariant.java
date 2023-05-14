package dat.backend.model.entities;

public class MaterialVariant {

    private int materialeVariantID;
    private int materialeID;
    private int length;
    private int partslistID;

    public MaterialVariant(int m_variantID, int materialeID, int length, int partslistID) {
        this.materialeVariantID = m_variantID;
        this.materialeID = materialeID;
        this.length = length;
        this.partslistID = partslistID;
    }

    public MaterialVariant(int materialeID, int length) {
        this.materialeID = materialeID;
        this.length = length;
        this.partslistID = partslistID;
    }

    public int getMaterialeVariantID() {
        return materialeVariantID;
    }

    public int getMaterialeID() {
        return materialeID;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPartslistID() {
        return partslistID;
    }

    @Override
    public String toString() {
        return "MaterialVariant{" +
                "materialeVariantID=" + materialeVariantID +
                ", materialeID=" + materialeID +
                ", length=" + length +
                ", partslistID=" + partslistID +
                '}';
    }
}

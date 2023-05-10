package dat.backend.model.entities;

public class Bom {
    int id;
    int price;
    int orderId;
    String beskrivelse;

    public Bom(int id, int price, int orderId, String beskrivelse) {
        this.id = id;
        this.price = price;
        this.orderId = orderId;
        this.beskrivelse = beskrivelse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString() {
        return "Bom{" +
                "id=" + id +
                ", price=" + price +
                ", orderId=" + orderId +
                ", beskrivelse='" + beskrivelse + '\'' +
                '}';
    }
}

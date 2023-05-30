package dat.backend.model.entities;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int lenght;
    private int width;
    private int totalPrice;
    private int status;
    private Timestamp date;
    private int userId;

    public Order(int orderId, int lenght, int width, int totalPrice, int status, Timestamp date, int userId) {
        this.orderId = orderId;
        this.lenght = lenght;
        this.width = width;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
        this.userId = userId;
    }



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getLenght() {
        return lenght;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Timestamp getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", lenght=" + lenght +
                ", width=" + width +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }
}

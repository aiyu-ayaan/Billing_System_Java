package model;

public class Items {
    private String id;
    private String product_name;
    private double price;


    public Items(String id, String product_name, double price) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
    }

    public Items() {
    }

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }


    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id='" + id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                '}';
    }
}

package model;

public class BillingItems {
    private final String id;
    private final String product_name;

    private final double price;

    private final int quantity;

    public BillingItems(String id, String product_name, double price, int quantity) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}

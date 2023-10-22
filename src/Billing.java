import database.DatabaseConnection;
import database.DatabaseConnectionImp;
import model.BillingItems;
import model.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Billing {

    private final List<BillingItems> billingItemsList = new ArrayList<>();

    private List<Items> items = new ArrayList<>();

    private Boolean isFirstTime = true;


    public Billing() {
    }

    public Billing(List<Items> items) {
        System.out.println("_________________________________________ Welcome to Ayaan Mart _________________________________________");
        this.items = items;
    }

    public void initSystem() {
//        only show when key is pressed
        if (!isFirstTime)
            new Scanner(System.in).nextLine();

        isFirstTime = false;

        System.out.println("_________________________________________ Items _________________________________________");
        System.out.println("\t\t\t\tId \t\t\t Name \t\t\t Price");
        for (Items items : items)
            System.out.println("\t\t\t\t"+items.getId() + "\t\t\t" + items.getProduct_name() + "\t\t\t" + items.getPrice());

        System.out.println("_________________________________________ 1. Add item to cart _________________________________________");
        System.out.println("_________________________________________ 2. Remove item from cart _________________________________________");
        System.out.println("_________________________________________ 3. View cart _________________________________________");
        System.out.println("_________________________________________ 4. Generate bill _________________________________________");
        System.out.println("_________________________________________ 5. Exit _________________________________________");
        System.out.println("_________________________________________ 6. Print Logs _________________________________________");
        System.out.print("Enter your choice :- ");
    }

    public void handleInput(int in){
        Scanner s1 = new Scanner(System.in);
        switch (in) {
            case 1 -> {
                System.out.println("Enter item id to add");
                String id = s1.nextLine();
                System.out.println("Enter quantity");
                int quantity = s1.nextInt();
                addBillingItems(id, quantity);
            }
            case 2 -> {
                System.out.println("Enter item id to remove");
                String id = s1.nextLine();
                removeBillingItems(id);
            }
            case 3 -> getCart();
            case 4 -> generateBill();
            case 5 -> System.exit(0);
            default -> System.out.println("Invalid input");
        }
    }

    public void addBillingItems(String id, int quantity) {
        Items items = this.items.stream().filter(items1 -> items1.getId().equals(id)).findFirst().orElse(null);
        if (items == null) {
            System.out.println("No item found with id: " + id);
            return;
        }
        BillingItems billingItems = mapToBillingItems(items, quantity);
        if (billingItemsList.stream().anyMatch(billingItems1 -> billingItems1.getId().equals(billingItems.getId()))) {
            System.out.println("Item already added to cart");
            return;
        }
        billingItemsList.add(billingItems);
        System.out.println("Item added to cart");
    }

    public void removeBillingItems(String id) {
        BillingItems billingItems = billingItemsList.stream().filter(billingItems1 -> billingItems1.getId().equals(id)).findFirst().orElse(null);
        if (billingItems == null) {
            System.out.println("No item found with id: " + id);
            return;
        }
        billingItemsList.remove(billingItems);
        System.out.println("Item removed from cart");
    }

    void getCart() {
        if (billingItemsList.isEmpty()) {
            System.out.println("No items added to cart");
            return;
        }
        System.out.println("Id \t\t\t Name \t\t\t Price \t\t\t Quantity");
        for (BillingItems billingItems : billingItemsList)
            System.out.println(billingItems.getId() + "\t\t\t" + billingItems.getProduct_name() + "\t\t\t" + billingItems.getPrice() + "\t\t\t" + billingItems.getQuantity());
    }

    private BillingItems mapToBillingItems(Items items, int quantity) {
        return new BillingItems(items.getId(), items.getProduct_name(), items.getPrice(), quantity);
    }

    void generateBill() {
        if (billingItemsList.isEmpty()) {
            System.out.println("No items added to cart");
            return;
        }
        System.out.println("_________________________________________ Invoice _________________________________________");
        System.out.println("_________________________________________ Ayaan Mart _________________________________________");
        System.out.println("_________________________________________ 123, ABC Road, XYZ City, PIN-123456 _________________________________________");
        System.out.println("_________________________________________ Phone: 1234567890 _________________________________________");
        System.out.println("Item Name\t\t\tPrice\t\t\tQuantity");
        for (BillingItems billingItems : billingItemsList)
            System.out.println(billingItems.getProduct_name() + "\t\t\t" + billingItems.getPrice() + "\t\t\t" + billingItems.getQuantity());

        int totalQuantity = billingItemsList.stream().mapToInt(BillingItems::getQuantity).sum();
        double totalPrice = billingItemsList.stream().mapToDouble(billingItems -> billingItems.getPrice() * billingItems.getQuantity()).sum();
        System.out.println("\nTotal Quantity: " + totalQuantity);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("_________________________________________ Thank you for shopping with us _________________________________________");
    }
}

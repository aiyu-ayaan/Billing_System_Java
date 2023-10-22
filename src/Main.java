import database.DatabaseConnectionImp;
import database.utils.Constants;
import model.BillingItems;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionImp db = DatabaseConnectionImp.getInstance();
        int status = db.connect();
        if (status == -1) {
            System.err.println(db.getLogs());
            System.exit(1);
        }
        var billing = new Billing(db.getAllItems());
        while (true){
            billing.initSystem();
            int choice  =new Scanner(System.in).nextInt();
            if (choice == 6){
                System.out.println(db.getLogs());
                continue;
            }
            billing.handleInput(choice);
        }

    }
}
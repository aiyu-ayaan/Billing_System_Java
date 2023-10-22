package database.utils;

import model.Items;

import java.util.ArrayList;
import java.util.List;

public abstract class Constants {
    public static List<Items> itemsList = new ArrayList<>(){{
        add(new Items("1", "Milk", 30));
        add(new Items("2", "Bread", 20));
        add(new Items("3", "Butter", 40));
        add(new Items("4", "Cheese", 50));
        add(new Items("5", "Eggs", 60));
        add(new Items("6", "Yogurt", 70));
        add(new Items("7", "Ice Cream", 80));
        add(new Items("8", "Chocolate", 90));
        add(new Items("9", "Candy", 100));
        add(new Items("10", "Cake", 110));
    }};
}

package database;

import model.Items;

import java.util.List;

public interface DatabaseConnection {

    String jbdcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
    String userName = "admin";
    String password = "admin";


    /**
     * Connect to database
     */
    int connect();


    /**
     * Get all items
     */
    List<Items> getAllItems();

    /**
     * Disconnect to database
     */
    int disconnect();

    /**
     * Get logs
     */
    String getLogs();



}

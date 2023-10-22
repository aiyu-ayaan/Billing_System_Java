package database;

import model.Items;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.utils.Constants.itemsList;

public class DatabaseConnectionImp implements DatabaseConnection {

    private static DatabaseConnectionImp INSTANCE = null;

    private  Connection con = null;
    private final StringBuilder logs;

    public static DatabaseConnectionImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConnectionImp();
        }
        return INSTANCE;
    }

    // Private constructor to avoid client applications to use constructor
    private DatabaseConnectionImp() {
        logs = new StringBuilder("\n\n---------------- LOGS ----------------\n\n");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            addLog("Error: unable to load driver class!");
            System.exit(1);
        }
    }

    private void addLog(String log) {
        logs.append(log).append("\n");
    }


    /**
     * Connect to database
     */
    @Override
    public int connect() {
        if (con != null) {
            addLog("Error: unable to connect to database! Please disconnect to database first");
            return -1;
        }
        try {
            con = DriverManager.getConnection(jbdcUrl, userName, password);
            addLog("Connected to database !!");
//            check table exist or not
            DatabaseMetaData dbm = con.getMetaData();
            if (dbm.getTables(null, null, "ITEMS", null).next()) {
                addLog("Table exist");
                checkTableIsEmpty(con);
            } else {
                createTable(con);
            }
        } catch (SQLException e) {
            addLog("Error: unable to connect to database! " + e.getMessage());
            System.exit(1);
        }
        return 1;
    }

    @Override
    public List<Items> getAllItems() {
        if (con == null) {
            addLog("Error: unable to get all items! Please connect to database first");
            return null;
        }
        List<Items> itemsList = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String product_name = rs.getString("product_name");
                double price = rs.getDouble("price");
                itemsList.add(new Items(id, product_name, price));
            }
        } catch (SQLException e) {
            addLog("Error: unable to get all items! " + e.getMessage());
        }
        return itemsList;
    }

    /**
     *  Create table
     * @param con Connection
     */
    private void createTable(Connection con) {
        try {
            String sql = "CREATE TABLE ITEMS (ID NUMBER(10) NOT NULL, " +
                    "PRODUCT_NAME VARCHAR2(50) NOT NULL, " +
                    "PRICE NUMBER(10) NOT NULL, " +
                    "PRIMARY KEY (ID))";
            con.createStatement().executeUpdate(sql);
            addLog("Table created");
            checkTableIsEmpty(con);
        } catch (SQLException e) {
            addLog("Error: unable to create table! " + e.getMessage());
        }
    }

    /**
     * Check table is empty or not
     * @param con Connection
     */
    private void checkTableIsEmpty(Connection con) {
        try {
            var rs = con.createStatement().executeQuery("SELECT * FROM ITEMS");
            if (rs.next()) {
                addLog("Table is not empty");
            } else {
                addDataToTable(con);
            }
        } catch (SQLException e) {
            addLog("Error: unable to check table is empty or not! " + e.getMessage());
        }
    }

    /**
     * Add data to table
     * @param con Connection
     */
    private void addDataToTable(Connection con) {
        String insertSql = "INSERT INTO ITEMS (id, product_name, price) VALUES (?, ?, ?)";
        try {
            con.setAutoCommit(false);
            var ps = con.prepareStatement(insertSql);
            for (Items items : itemsList) {
                ps.setString(1, items.getId());
                ps.setString(2, items.getProduct_name());
                ps.setDouble(3, items.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
            addLog("Data added to table");
        } catch (SQLException e) {
            addLog("Error: unable to add data to table! " + e.getMessage());
        }
    }

    @Override
    public int disconnect() {
        if (con == null) {
            addLog("Error: unable to disconnect to database! Please connect to database first");
            return -1;
        }
        try {
            Connection con = DriverManager.getConnection(jbdcUrl, userName, password);
            con.close();
            addLog("Disconnected from database !!");
        } catch (SQLException e) {
            addLog("Error: unable to disconnect to database! " + e.getMessage());
        }
        return 1;
    }

    @Override
    public String getLogs() {
        return logs.append("\n\n---------------- END ----------------\n\n").toString();
    }


}

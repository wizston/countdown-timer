package org.anelda.isakacountdowntimer.database;

import java.sql.*;
public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getDatabaseConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            databaseLink = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/anelda/isakacountdowntimer/database/test.db");
            System.out.println("Opened database successfully");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return databaseLink;
    }
}

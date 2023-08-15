package org.anelda.wizston.countdowntimer.database;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getDatabaseConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            String userHome = System.getProperty("user.home");
            Path applicationDir = Paths.get(userHome, ".wizstonCountDown");
            Files.createDirectories(applicationDir);
            Path databaseFile = applicationDir.resolve("nnFPNxpz16fY13Lusvt9");

            databaseLink = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.toAbsolutePath());
            System.out.println("Opened database successfully");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}

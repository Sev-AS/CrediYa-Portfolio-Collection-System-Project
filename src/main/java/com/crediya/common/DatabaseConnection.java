package com.crediya.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/CrediYa_Portfolio_Collection_System_Project?createDatabaseIfNotExist=true";
        String user = "root";
        String password = "admin";
        return DriverManager.getConnection(url, user, password);
    }
}

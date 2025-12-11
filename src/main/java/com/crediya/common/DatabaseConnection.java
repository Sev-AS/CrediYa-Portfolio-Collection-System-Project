package com.crediya.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/crediya_db";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}

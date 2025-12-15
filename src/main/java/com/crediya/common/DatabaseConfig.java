package com.crediya.common;

public class DatabaseConfig {
    
    public static final String DATABASE_NAME = "crediya_db";
    
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "admin";
    
    public static final int DB_PORT = 3306;
    
    private DatabaseConfig() {
        throw new UnsupportedOperationException("Esta clase no debe ser instanciada");
    }
}
package com.project.api.v1.dbinterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Used for SQLite. Not needed with Supabase.
public class DbConnection {

    private static final String DB_URL = "jdbc:sqlite:boolean-broker-backend/database/app.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to SQLite", e);
        }
    }
}

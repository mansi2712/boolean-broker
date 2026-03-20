package com.project.api.v1.dbinterface;

import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;

@ApplicationScoped
public class DbInitialise {

    @Inject
    DataSource dataSource;

    public void initialize() {
        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();) {
            Log.infof("Initializing Database");
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS USER_INFORMATION (
                    User_ID VARCHAR PRIMARY KEY,
                    User_Name VARCHAR NOT NULL,
                    User_Type VARCHAR NOT NULL,
                    Date_Of_Birth DATE,
                    Email_ID TEXT ,
                    Phone_Number VARCHAR(10) NOT NULL,
                    PAN_Number VARCHAR(10) UNIQUE NOT NULL,
                    Permanent_Address TEXT
                )
            """);

//            stmt.execute("""
//                CREATE TABLE IF NOT EXISTS BANK_DETAILS (
//                    User_ID VARCHAR,
//                    Account_Number VARCHAR PRIMARY KEY,
//                    IFSC_Code VARCHAR NOT NULL,
//                    Provider TEXT,
//                    Is_Primary BOOLEAN,
//                    FOREIGN KEY(user_id) REFERENCES USER_INFORMATION(id)
//                )
//            """);
//
//            stmt.execute("""
//                CREATE TABLE IF NOT EXISTS NOMINEE_DETAILS (
//                    User_ID VARCHAR,
//                    Nominee_Name VARCHAR NOT NULL,
//                    Relation VARCHAR,
//                    Date_Of_Birth DATE NOT NULL,
//                    Email_ID TEXT,
//                    Phone_Number BIGINT,
//                    Permanent_Address TEXT,
//                    Share_Percentage NUMERIC NOT NULL,
//                    FOREIGN KEY(user_id) REFERENCES USER_INFORMATION(id)
//                )
//            """);

            Log.infof("Database initialized");

        } catch (Exception e) {
            Log.infof("Error initializing database: %s", e.getMessage());
            e.printStackTrace();
        }
    }
}


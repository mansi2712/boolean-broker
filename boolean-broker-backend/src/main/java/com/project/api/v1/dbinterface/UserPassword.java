package com.project.api.v1.dbinterface;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import io.quarkus.logging.Log;

@ApplicationScoped
public class UserPassword {

    @Inject
    DataSource datasource;

    public String retrieveHashedPassword(String userId) {
        String sql = "Select User_ID, Password_Hash FROM USER_PASSWORD WHERE User_ID = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);
             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     String hash_password = rs.getString("Password_Hash");
                     Log.infof("Hash retrieved!");

                     return hash_password;
                 }
             }
        }
        catch (Exception e) {
            Log.infof("Failed to retrieve user password");
        }
    }

    public void insertHashedPassword(String userId, String panNumber, String phone, String hashedPassword) {
        String sql = "INSERT INTO USER_PASSWORD(User_ID, PAN_Number, Phone_Number, Password_Hash) VALUES(?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, panNumber);
            ps.setString(3, phone);
            ps.setString(4, hashedPassword);
            ps.executeUpdate();
        }
        catch (Exception e) {
            Log.infof("Failed to insert user password %s", e.getMessage());
        }
    }

    public void updateHashedPasswordWithUserId(String userId, String hashedPassword) {
        String sql = "UPDATE USER_PASSWORD SET Password_Hash = ? WHERE User_ID = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, hashedPassword);
                ps.setString(2, userId);
                ps.executeUpdate();
        }
        catch (Exception e) {
            Log.infof("Failed to update user password %s", e.getMessage());
        }
    }

    public void updateHashedPasswordWithPAN(String panNumber, String hashedPassword) {
        String sql = "UPDATE USER_PASSWORD SET Password_Hash = ? WHERE PAN_Number = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, panNumber);
            ps.executeUpdate();
        }
        catch (Exception e) {
            Log.infof("Failed to update user password %s", e.getMessage());
        }
    }

    public void updateHashedPasswordWithPhone(String phone, String hashedPassword) {
        String sql = "UPDATE USER_PASSWORD SET Password_Hash = ? WHERE Phone_Number = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, phone);
            ps.executeUpdate();
        }
        catch (Exception e) {
            Log.infof("Failed to update user password %s", e.getMessage());
        }
    }
}
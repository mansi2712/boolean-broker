package com.project.api.v1.dbinterface;

import com.project.api.v1.model.entity.UserEntity;

import com.project.api.v1.model.entity.UserType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import io.quarkus.logging.Log;

@ApplicationScoped
public class UserInformationUpdate {

    @Inject
    DataSource dataSource;

    public UserEntity retrieve_user_information(String user_id) {
        String sql = "SELECT User_ID, User_Name, User_Type, Date_Of_Birth, Email_ID, Phone_Number, PAN_Number, Permanent_Address FROM USER_INFORMATION WHERE User_ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user_id);
                try (ResultSet rs =ps.executeQuery()) {
                    if (rs.next()) {
                        String user_type = rs.getString("User_Type");
                        UserEntity user = new UserEntity(
                                rs.getString("User_Name"),
                                rs.getString("Phone_Number"),
                                UserType.valueOf(user_type),
                                rs.getString("PAN_Number")
                        );
                        user.setUserId(rs.getString("User_ID"));
                        user.setDateOfBirth(rs.getDate("Date_Of_Birth"));
                        user.setEmail(rs.getString("Email_ID"));
                        user.setPermanentAddress(rs.getString("Permanent_Address"));

                        return user;
                    }
            }
        }
        catch (IllegalArgumentException ex) {
            Log.infof("Incorrect data found.");
        }
        catch (Exception e) {
            Log.infof("Failed to retrieve user information");
        }

        return null;
    }

    public void insert_user_information(UserEntity user) {

        String sql = "INSERT INTO USER_INFORMATION(User_ID, User_Name, User_Type, Date_Of_Birth, Email_ID, Phone_Number, PAN_Number, Permanent_Address) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.getUserId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getUserType().toString());
                ps.setDate(4, user.getDateOfBirth());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getPhone());
                ps.setString(7, user.getPanNumber());
                ps.setString(8, user.getPermanentAddress());
                ps.executeUpdate();
            }
        catch (Exception e) {
            Log.infof("Failed to insert user information %s", e.getMessage());
        }
    }

    public void update_user_information(UserEntity user) {
        String sql = "UPDATE USER_INFORMATION SET User_Name = ?, User_Type = ?, Date_Of_Birth = ?, Email_ID = ?, Phone_Number = ?, PAN_Number = ?, Permanent_Address = ? WHERE User_ID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getUserType().toString());
                ps.setDate(3, user.getDateOfBirth());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPhone());
                ps.setString(6, user.getPanNumber());
                ps.setString(7, user.getPermanentAddress());
                ps.setString(8, user.getUserId());
                ps.executeUpdate();
        }
        catch (Exception e) {
            Log.infof("Failed to update user information for userId " + user.getUserId());
        }
    }

    public void remove_user_information(String user_id) {
        String sql = "DELETE FROM USER_INFORMATION WHERE User_ID = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user_id);
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted == 0) {
                    Log.infof("User not found");
                }
                else {
                    Log.infof("User with user id " + user_id + " deleted");
                }
            }
        catch (Exception e) {
            Log.infof("Failed to delete user information");
        }
    }

}

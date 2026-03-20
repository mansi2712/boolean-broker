package com.project.api.v1.model.entity;

import java.sql.Date;

public class UserEntity {

    private String userId;          // PK
    private String name;
    private UserType userType;    // ADMIN / BROKER / TRADER
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String panNumber;     // nullable for ADMIN
    private String permanentAddress;
    private boolean verified = false;

    //constructors
    public UserEntity(String name, String phone, UserType userType, String panNumber){

        //Validation checks to make sure that the mandatory fields are filled in properly
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone is required");
        }

        if (userType == null) {
            throw new IllegalArgumentException("User type is required");
        }

        if (panNumber == null || panNumber.isBlank() || panNumber.length() != 10) {
            throw new IllegalArgumentException("Invalid PAN format");
        }

        this.name = name;
        this.phone = phone;
        this.userType = userType;
        this.panNumber = panNumber;
    }

    // --- getters & setters ---

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

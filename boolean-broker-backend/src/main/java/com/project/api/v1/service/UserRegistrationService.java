package com.project.api.v1.service;

import com.project.api.v1.model.dto.UserRegistrationRequest;
import com.project.api.v1.model.entity.UserEntity;
import com.project.api.v1.model.entity.UserType;
import jakarta.enterprise.context.RequestScoped;
//import com.project.api.v1.dbinterface.DbInitialise;
import com.project.api.v1.dbinterface.UserInformationUpdate;
import jakarta.inject.Inject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.sql.Date;

@RequestScoped
public class UserRegistrationService {

    @Inject
    OtpService otpService;

//    @Inject
//    DbInitialise dbInitialise;

    @Inject
    UserInformationUpdate userInfo;

    public String RegisterUser(UserRegistrationRequest userRegistrationRequest ){

        String name = userRegistrationRequest.getName();
        String phone = userRegistrationRequest.getPhone();
        UserType userType = userRegistrationRequest.getUserType();
        String panNumber = userRegistrationRequest.getPanNumber();
        String userID = generateUserID(name,phone,panNumber);

        UserEntity userEntity = new UserEntity(name,phone,userType,panNumber);
        //UserID will be set only after successful creation of a UserEntity object
        userEntity.setUserId(userID);
        userEntity.setDateOfBirth(Date.valueOf("1990-03-20"));

        //Add user to userInformation table
        addUserToDB(userEntity);

        //generate and store OTP
        return otpService.generateAndStoreOtp(userID);
    }

    public void addUserToDB(UserEntity userEntity){
//        dbInitialise.initialize();
        userInfo.insert_user_information(userEntity);
        //SQL command to INSERT userEntity into User Information table
        System.out.println("Insert user into userInformation. Name: "+userEntity.getName()+" and userID: "+userEntity.getUserId());
    }

    public String generateUserID(String name, String phone, String panNumber){
        String userInput = normalize(name,phone,panNumber);
        byte[] hash = sha256(userInput);
        String userID = encodeBase64(hash);

        return userID.substring(0,15); //because we want the userID to be a maximum of 15 characters
    }

    private String normalize(String name, String phone, String panNumber){
        return(name.trim().toUpperCase() + "|"
                + phone.trim() + "|"
                +panNumber.trim().toUpperCase());
    }

    private static byte[] sha256(String userInput) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(userInput.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeBase64(byte[] hash) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(hash);
    }
}

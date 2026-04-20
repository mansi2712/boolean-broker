package com.project.api.v1.service;

import com.project.api.v1.model.entity.UserEntity;
import io.quarkus.logging.Log;

@ApplicationScoped
public class UserPasswordService {

    @Inject
    JwtService jwtService;

    @Inject
    UserInformationUpdate userInfo;

    @Inject
    UserPassword userPassword;

    @Inject
    HashingTechniques hashingTechniques;

    public String extractUserId(TokenPair accessToken) {
        String userId = jwtService.extractUserId(accessToken);
        Log.infof("User Id extracted from token: %s", userId);
        return userId
    }

    public void InsertUserPassword(TokenPair accessToken, String inputPassword) {
        String userId = extractUserId(accessToken);
        UserEntity user = userInfo.retrieve_user_information(userId);
        hashPassword = hashingTechniques.hashPassword(inputPassword)
        userPassword.insertHashedPassword(
            user.getUserId(),
            user.getPanNumber(),
            user.getPhone(),
            hashPassword
        )
    }

    public void UpdateUserPassword(String oldPassword, String newPassword) {
        String userId = extractUserId(accessToken);
        hashedDbPassword = userPassword.retrieveHashedPassword(userId);
        if ! (hashingTechniques.verifyPassword(oldPassword, hashedDbPassword)){
            throw new RuntimeException("Old password does not match.")
        }
        hashedNewPassword = hashingTechniques.hashPassword(newPassword)
        userPassword.updateHashedPasswordWithUserId(userId, newPassword)
    }

    public void ForgotUserPassword(String userId) {
        String randomPassword = "";
        hashPassword = hashingTechniques.hashPassword(randomPassword)
        userPassword.updateHashedPasswordWithUserId(userId, hashPassword);
        //TODO: Logic to send new system generated password to email or phone?
        Log.infof("New password generated.");
    }

    // Function to allow forgot password with phone number and pan number

}
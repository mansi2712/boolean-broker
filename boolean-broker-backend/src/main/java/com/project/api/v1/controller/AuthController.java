package com.project.api.v1.controller;


import com.project.api.v1.model.dto.OtpVerificationRequest;
import com.project.api.vi.model.dto.CreatePasswordRequest;
import com.project.api.v1.model.dto.TokenPair;
import com.project.api.v1.service.OtpService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class AuthController {

    @Inject
    OtpService otpService;

    @Inject
    UserPasswordService userPasswordService;


    @POST
    @Path("/verifyotp")
    public Response VerifyOtp(@Valid OtpVerificationRequest request) {
        Log.infof("In verifyOtp method");
        TokenPair tokenPair = otpService.VerifyOtp(request);
        Log.infof("Otp verification response: %s", tokenPair);
        return Response.status(Response.Status.OK).entity(tokenPair).build();
    }

    @POST
    @Path("/create/password")
    public Response CreatePassword(@Valid CreatePasswordRequest request) {
        Log.infof("Creating a password");

    }
}
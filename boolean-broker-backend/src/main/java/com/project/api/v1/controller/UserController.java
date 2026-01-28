package com.project.api.v1.controller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/*
This is a controller class for user related endpoints
 */

import com.project.api.v1.model.dto.UserRegistrationRequest;
import com.project.api.v1.model.dto.UserRegistrationResponse;
import com.project.api.v1.service.UserRegistrationService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class UserController {


    private final UserRegistrationService userRegistrationService;
//    private final UpdateProfileService updateProfileService;

    @Inject
    public UserController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
//        this.updateProfileService = updateProfileService;
    }


    @POST
    @Path("/registration")
    @Tag(name = "user")
    @Operation(summary = "Register the user", description = "Returns a verification code after registering the user")
    @APIResponse(responseCode = "201", description = "User added successfully")
    public Response register(@Valid UserRegistrationRequest request) {
        try {

            String verificationToken = userRegistrationService.RegisterUser(request);

            UserRegistrationResponse userRegistrationResponse =
                    new UserRegistrationResponse(verificationToken);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(userRegistrationResponse)
                    .build();
        }
        catch (Exception e) {
            return Response.
                    status(500).
                    entity(e).
                    build();
        }
    }

//    @POST
//    @Path("/profileupdate")
//    public Response register(@Valid UpdateUserProfileRequest request) {
//        updateProfileService.UpdateUserProfile(request);
//        return Response.status(Response.Status.CREATED).build();
//    }
}

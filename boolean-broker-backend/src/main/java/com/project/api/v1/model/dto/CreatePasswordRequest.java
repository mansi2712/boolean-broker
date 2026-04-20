package com.project.api.v1.model.dto;

public class CreatePasswordRequest {

    @NotNull
    private String

    @NotBlank(message = "Password can't be empty")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
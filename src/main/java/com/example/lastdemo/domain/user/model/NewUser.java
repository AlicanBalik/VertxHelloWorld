package com.example.lastdemo.domain.user.model;

import com.example.lastdemo.application.constants.ValidationMessages;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public final class NewUser {

    @Email(message = "Invalid email format.")
    @NotBlank(message = ValidationMessages.EMAIL_MUST_BE_NOT_BLANK)
    private final String email;

    @NotBlank(message = ValidationMessages.PASSWORD_MUST_BE_NOT_BLANK)
    private final String password;

    public NewUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

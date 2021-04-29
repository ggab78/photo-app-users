package com.gabriel.photoappusers.ui.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
public class CreateUserRequestModel {

    @NotNull(message = "first name can't be null")
    @Size(min=3, message = "first name must be 2 characters minimum")
    private String firstName;

    @NotNull(message = "last name can't be null")
    @Size(min=3, message = "last name must be 2 characters minimum")
    private String lastName;

    @NotNull(message = "password can't be null")
    @Size(min=4, max=8, message = "password lenght must be between 4 and 8 characters")
    private String password;

    @NotNull(message = "email can't be null")
    @Email
    private String email;
}

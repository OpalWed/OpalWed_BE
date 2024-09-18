package com.exe201.opalwed.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    String password;

    @NotNull
    private String fullName;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private String description = "";

    private String imageUrl = "";

}

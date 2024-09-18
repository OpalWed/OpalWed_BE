package com.exe201.opalwed.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty
    @Email
    String email;

    @NotEmpty
    String password;
}

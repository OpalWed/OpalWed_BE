package com.exe201.opalwed.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    String email;

    @NotEmpty(message = "Password không được để trống")
    String password;
}

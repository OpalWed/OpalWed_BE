package com.exe201.opalwed.security;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Email không được để trống!")
    String email;

    @NotEmpty(message = "Mật khẩu không được để trống")
    String password;
}

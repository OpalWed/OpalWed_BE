package com.exe201.opalwed.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordOTP {

    @NotEmpty(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    String email;

    @NotEmpty(message = "Mật khẩu không được để trống!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Mật khẩu không hợp lê!")
    String newPassword;

    @NotBlank
    @Pattern(regexp = "^\\d{6}$", message = "OTP không hợp lệ!")
    String otp;
}

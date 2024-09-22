package com.exe201.opalwed.security;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    String email;

    @NotEmpty(message = "Mật khẩu không được để trống!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Số điện thoại không hợp lê!")
    String password;

    @NotNull(message = "Tên không được để trống!")
    private String fullName;

    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ!")
    private String phone;

    @NotNull(message = "Địa chỉ không được để trống")
    private String address;

    private String description = "";

    private String imageUrl = "";

}

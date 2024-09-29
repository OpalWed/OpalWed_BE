package com.exe201.opalwed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProfileRequest {
    @NotBlank(message = "Tên không được để trống!")
    String fullName;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ!")
    String phone;

    @NotBlank(message = "Địa chỉ không được để trống!")
    String address;

    String description = "";

    String imageUrl = "";

}

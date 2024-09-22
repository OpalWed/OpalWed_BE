package com.exe201.opalwed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class PartnerInformationDTO {

    Long informationId;

    // Account account; dto later?

    @NotBlank(message = "Tên không được để trống!")
    String fullName;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ!")
    String phone;

    @NotBlank(message = "Địa chỉ không được để trống!")
    String address;

    String description = "";

    String imageUrl = "";

    Long partnerId;

    Set<PartnerUtilityDTO> utilities;

    String status;

    Set<PartnerImageDTO> images;

    int successEvent;

    String note = "";

}

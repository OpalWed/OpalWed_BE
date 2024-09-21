package com.exe201.opalwed.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    String fullName;

    @NotBlank
    String phone;

    @NotBlank
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

package com.exe201.opalwed.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerApplicationRequest {

    private String fullName;

    private String phone;

    private String email;

    private LocalDateTime weddingDate;

    private String weddingLocation;

    private Integer numberOfGuests;

    private String weddingDescription;

}

package com.exe201.opalwed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationDTO {

    private Long applicationId;

    private Long userId;
    private String fullName;
    @NotNull(message = "Wedding date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime weddingDate;

    @NotBlank(message = "Wedding location cannot be empty")
    private String weddingLocation;

    @Min(value = 1, message = "Number of guests must be at least 1")
    @Max(value = 1000, message = "Number of guests must not exceed 1000")
    private Integer numberOfGuests;

    @Size(max = 500, message = "Wedding description must not exceed 500 characters")
    private String weddingDescription;

    @NotNull(message = "Required services file cannot be null")
    @Size(max = 255, message = "Required services file name must not exceed 255 characters")
    private String requiredServicesFile;

    private String status;

    private String createdDate;

}

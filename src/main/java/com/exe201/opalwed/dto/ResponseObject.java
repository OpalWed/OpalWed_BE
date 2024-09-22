package com.exe201.opalwed.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseObject(
        Object data,
        Boolean isSuccess,
        String message,
        HttpStatus status
) {

}
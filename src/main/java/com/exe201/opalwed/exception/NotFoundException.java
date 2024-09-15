package com.exe201.opalwed.exception;

import com.exe201.opalwed.dto.response.ResponseObject;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("NOT_FOUND")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }

}

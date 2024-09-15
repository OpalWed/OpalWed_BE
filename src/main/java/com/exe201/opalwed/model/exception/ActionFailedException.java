package com.exe201.opalwed.model.exception;

import com.exe201.opalwed.model.dto.response.ResponseObject;
import org.springframework.http.HttpStatus;


public class ActionFailedException extends BaseException {

    public ActionFailedException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("ACTION_FAILED")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }

    public ActionFailedException(String message, String code) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code(code)
                .data(null)
                .message(message)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }

}
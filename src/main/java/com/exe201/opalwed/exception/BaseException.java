package com.exe201.opalwed.exception;

import com.exe201.opalwed.dto.response.ResponseObject;

public class BaseException extends RuntimeException {
    protected ResponseObject errorResponse;

    protected BaseException(String message) {
        super(message);
    }

    public ResponseObject getErrorResponse() {
        return errorResponse;
    }
}
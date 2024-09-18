package com.exe201.opalwed.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OpalException extends RuntimeException {

    private final Map<String, String> errorsMap = new HashMap<>();

    public OpalException() {
    }

    public OpalException(String message) {
        super(message);
    }

    public void addErrors(String field, String msg) {
        errorsMap.put(field, msg);
    }

}

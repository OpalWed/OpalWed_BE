package com.exe201.opalwed.exception;

import com.exe201.opalwed.dto.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class OpalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(OpalExceptionHandler.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseObject> handleInvalidArgument(
            MethodArgumentNotValidException invalidEx) {

        Map<String, Object> responseMap = new HashMap<>();
        Map<String, String> errorMap = new HashMap<>();

        //argument invalid
        invalidEx.getBindingResult().getFieldErrors()
                .forEach(error -> errorMap.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        responseMap.put("errors", errorMap);

        ResponseObject responseObject = ResponseObject.builder()
                .message("Invalid argument")
                .isSuccess(false)
                .data(responseMap)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.ok().body(responseObject);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({OpalException.class})
    public ResponseEntity<ResponseObject> handleJourneyConstraintException(
            OpalException invalidEx) {
        logger.info("Handling OpalException: {}", invalidEx.getMessage(), invalidEx);

        Map<String, Object> responseMap = new HashMap<>();
        Map<String, String> errorMap = invalidEx.getErrorsMap();

        responseMap.put("errors", errorMap);
        responseMap.put("message", invalidEx.getMessage());

        ResponseObject responseObject = ResponseObject.builder()
                .message("Invalid argument")
                .isSuccess(false)
                .data(responseMap)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }
}

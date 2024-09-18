package com.exe201.opalwed.exception;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> handleInvalidArgument(
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
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({OpalException.class})
    //This handler used for exception in service layer
    public ResponseEntity<Map<String, Object>> handleJourneyConstraintException(
            OpalException invalidEx) {
        logger.info("Handling OpalException: {}", invalidEx.getMessage(), invalidEx);

        Map<String, Object> responseMap = new HashMap<>();
        Map<String, String> errorMap = invalidEx.getErrorsMap();

        responseMap.put("errors", errorMap);
        responseMap.put("message", invalidEx.getMessage());
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }
}

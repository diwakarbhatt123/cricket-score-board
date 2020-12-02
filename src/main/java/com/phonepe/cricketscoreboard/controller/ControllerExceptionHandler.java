package com.phonepe.cricketscoreboard.controller;

import com.phonepe.cricketscoreboard.common.exception.CricketGameException;
import com.phonepe.cricketscoreboard.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class ControllerExceptionHandler {

    public static final String ERROR_MESSAGE = "ERROR";

    @ExceptionHandler(CricketGameException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCricketException(CricketGameException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("CR-101")
                .message(exception.getMessage())
                .build();
        errorResponse.setStatus(ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("CR-101")
                .message(exception.getMessage())
                .build();
        errorResponse.setStatus(ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

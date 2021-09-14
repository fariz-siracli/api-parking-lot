package com.fs.api.parking.lot.rest;

import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.exception.ExceptionResponse;
import com.fs.api.parking.lot.logger.DPLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    private static final DPLogger logger = DPLogger.getLogger(ErrorHandler.class);

    @ExceptionHandler(DPException.class)
    public ResponseEntity<ExceptionResponse> handle(DPException ex) {
        logger.error("DPException", ex);
        ExceptionResponse response = new ExceptionResponse(ex.getErrorCode(), ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handle(Exception ex) {
        logger.error("Exception", ex);
        ExceptionResponse response = new ExceptionResponse("exception.unexpected",
                "Unexpected error occured");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

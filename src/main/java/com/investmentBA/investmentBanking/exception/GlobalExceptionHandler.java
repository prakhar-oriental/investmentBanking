package com.investmentBA.investmentBanking.exception;

import com.investmentBA.investmentBanking.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidNullObj.class)
    public ResponseEntity<ErrorResponse> NullObjFound(InvalidNullObj ex) {
        ErrorResponse response = new ErrorResponse("NULL_OBJ_FOUND", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce("", (msg1, msg2) -> msg1 + (msg1.isEmpty() ? "" : ", ") + msg2);

        ErrorResponse response = new ErrorResponse("VALIDATION_FAILED", errorMessages);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

package com.users.api.exceptions;

import com.users.api.models.responses.ErrorResponse;
import com.users.api.models.responses.GeneralResponse;
import com.users.api.models.responses.ErrorsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(ErrorResponse::new)
                .toList();

        if(errors.size() == 1){
            return ResponseEntity.badRequest().body(errors.get(0));
        }

        return ResponseEntity.badRequest().body(new ErrorsResponse(errors));
    }

    @ExceptionHandler(MissingConfigurationException.class)
    public ResponseEntity<GeneralResponse> handleMissingConfigurationException(MissingConfigurationException ex) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }
}

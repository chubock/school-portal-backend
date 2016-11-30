package com.avin.schoolportal.rest;

import com.avin.schoolportal.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/30/2016.
 */

@RestControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<FieldErrorDTO> processValidationError(MethodArgumentNotValidException ex) {
        List<FieldErrorDTO> ret = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        result.getFieldErrors().forEach(fieldError -> {
            ret.add(new FieldErrorDTO(fieldError.getDefaultMessage(),fieldError.getField()));
        });
        return ret;
    }
}

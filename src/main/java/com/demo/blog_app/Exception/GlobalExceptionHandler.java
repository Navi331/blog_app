package com.demo.blog_app.Exception;

import com.demo.blog_app.Payload.ExceptionDetailsForValidation;
import com.demo.blog_app.Payload.ExceptionalDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            Map<String, String> map = new HashMap<String, String>();
            ex.getBindingResult().getFieldErrors().forEach(error->{
                map.put(error.getField(),error.getDefaultMessage());
            });
        ExceptionDetailsForValidation ed = new ExceptionDetailsForValidation("Validating Failed",
                map, request.getDescription(false),new Date());
            return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        ExceptionalDetails exceptionalDetails = new ExceptionalDetails(
                request.getDescription(false), exception.getMessage(), new Date());
        return new ResponseEntity<>(exceptionalDetails, HttpStatus.NOT_ACCEPTABLE);
    }
}


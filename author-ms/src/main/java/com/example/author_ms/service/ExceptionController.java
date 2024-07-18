package com.example.author_ms.service;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", HttpStatus.NOT_FOUND.value());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, Object>> handleFeignException(FeignException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "Error fetching data");
        response.put("request", extractFeignRequestDetails(exception));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> extractFeignRequestDetails(FeignException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("method", exception.request().httpMethod().name());
        response.put("url", exception.request().url());
        response.put("serviceMethod", exception.request().method());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

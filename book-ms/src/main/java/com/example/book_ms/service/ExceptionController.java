package com.example.book_ms.service;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", HttpStatus.NOT_FOUND.value());
        map.put("message", exception.getMessage());
        return map;
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleFeignException(FeignException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("message", "Error fetching data");
        map.put("request", extractFeignRequestDetails(exception));
        return map;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("message", exception.getMessage());
        return map;
    }

    private Map<String, String> extractFeignRequestDetails(FeignException exception) {
        Map<String, String> requestDetails = new HashMap<>();
        requestDetails.put("method", exception.request().httpMethod().name());
        requestDetails.put("url", exception.request().url());
        requestDetails.put("serviceMethod", exception.request().method());
        return requestDetails;
    }
}

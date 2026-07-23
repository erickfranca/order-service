package com.efranca.orderservice.controller;

import com.efranca.orderservice.service.OrderPaymentDeclinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderPaymentDeclinedException.class)
    public ResponseEntity<Map<String, String>> handleDeclined(OrderPaymentDeclinedException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(Map.of("error", ex.getMessage()));
    }
}

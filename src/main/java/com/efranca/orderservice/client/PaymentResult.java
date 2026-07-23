package com.efranca.orderservice.client;

public record PaymentResult(String paymentId, String orderId, Double amount, String status) {
}

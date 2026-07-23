package com.efranca.orderservice.model;

import java.time.Instant;

public class Order {

    private final String orderId;
    private final String productId;
    private final Double amount;
    private final OrderStatus status;
    private final String paymentId;
    private final String trackingCode;
    private final Instant createdAt;

    public Order(String orderId, String productId, Double amount, OrderStatus status, String paymentId, String trackingCode) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.status = status;
        this.paymentId = paymentId;
        this.trackingCode = trackingCode;
        this.createdAt = Instant.now();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public Double getAmount() {
        return amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

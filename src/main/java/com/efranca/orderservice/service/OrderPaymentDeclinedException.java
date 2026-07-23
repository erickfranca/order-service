package com.efranca.orderservice.service;

public class OrderPaymentDeclinedException extends RuntimeException {

    public OrderPaymentDeclinedException(String orderId) {
        super("Order " + orderId + " could not be completed: payment declined");
    }
}

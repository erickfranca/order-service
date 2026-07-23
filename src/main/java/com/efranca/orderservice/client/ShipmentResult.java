package com.efranca.orderservice.client;

public record ShipmentResult(String shipmentId, String orderId, String address, String trackingCode) {
}

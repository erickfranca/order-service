package com.efranca.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ShippingClient {

    private final RestClient restClient;

    public ShippingClient(@Qualifier("shippingRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public ShipmentResult schedule(String orderId, String address) {
        return restClient.post()
                .uri("/shipments")
                .body(new ScheduleShipmentRequest(orderId, address))
                .retrieve()
                .body(ShipmentResult.class);
    }

    private record ScheduleShipmentRequest(String orderId, String address) {
    }
}

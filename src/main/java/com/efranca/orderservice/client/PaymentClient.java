package com.efranca.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PaymentClient {

    private final RestClient restClient;

    public PaymentClient(@Qualifier("paymentRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public PaymentResult authorize(String orderId, Double amount) {
        return restClient.post()
                .uri("/payments")
                .body(new AuthorizePaymentRequest(orderId, amount))
                .retrieve()
                .body(PaymentResult.class);
    }

    private record AuthorizePaymentRequest(String orderId, Double amount) {
    }
}

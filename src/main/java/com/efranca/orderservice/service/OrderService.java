package com.efranca.orderservice.service;

import com.efranca.orderservice.client.PaymentClient;
import com.efranca.orderservice.client.PaymentResult;
import com.efranca.orderservice.client.ShipmentResult;
import com.efranca.orderservice.client.ShippingClient;
import com.efranca.orderservice.model.Order;
import com.efranca.orderservice.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Este e o ponto central do exercicio de tracing: uma unica requisicao
 * de entrada (POST /orders) dispara DUAS chamadas HTTP sincronas para
 * processos diferentes (payment-service, depois shipping-service).
 *
 * Sem tracing: se algo for lento ou falhar, voce só sabe que "o pedido
 * falhou" - tem que ir manualmente nos logs de cada servico e tentar
 * correlacionar por horario, o que é um pesadelo em producao.
 *
 * Com tracing: as tres chamadas (order -> payment, order -> shipping)
 * viram spans dentro de um mesmo trace, com um trace-id compartilhado.
 * Voce vai literalmente ver essa cadeia como uma arvore no Grafana Tempo.
 */
@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final PaymentClient paymentClient;
    private final ShippingClient shippingClient;
    private final List<Order> orders = new CopyOnWriteArrayList<>();

    public OrderService(PaymentClient paymentClient, ShippingClient shippingClient) {
        this.paymentClient = paymentClient;
        this.shippingClient = shippingClient;
    }

    public Order placeOrder(String productId, Double amount, String shippingAddress) {
        String orderId = UUID.randomUUID().toString();
        log.info("Processing order {} for product {}", orderId, productId);

        PaymentResult payment;
        try {
            payment = paymentClient.authorize(orderId, amount);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() != HttpStatus.PAYMENT_REQUIRED) {
                throw ex;
            }
            Order declined = new Order(orderId, productId, amount, OrderStatus.PAYMENT_DECLINED, null, null);
            orders.add(declined);
            throw new OrderPaymentDeclinedException(orderId);
        }

        ShipmentResult shipment = shippingClient.schedule(orderId, shippingAddress);

        Order order = new Order(
                orderId,
                productId,
                amount,
                OrderStatus.COMPLETED,
                payment.paymentId(),
                shipment.trackingCode()
        );
        orders.add(order);
        return order;
    }

    public List<Order> findAll() {
        return orders;
    }
}

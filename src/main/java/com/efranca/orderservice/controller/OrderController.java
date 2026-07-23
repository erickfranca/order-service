package com.efranca.orderservice.controller;

import com.efranca.orderservice.model.Order;
import com.efranca.orderservice.model.OrderRequest;
import com.efranca.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request.getProductId(), request.getAmount(), request.getShippingAddress());
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }
}

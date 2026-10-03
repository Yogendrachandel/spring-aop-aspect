package com.learn.controller;

import com.learn.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Test:
    // GET http://localhost:8080/api/orders/create?customer=Yogen
    @GetMapping("/api/orders/create")
    public String createOrder(
            @RequestParam(defaultValue = "Yogen") String customer) {

        return orderService.createOrder(customer);
    }

    // Test:
    // GET http://localhost:8080/api/orders/101
    @GetMapping("/api/orders/{id}")
    public String getOrder(
            @org.springframework.web.bind.annotation.PathVariable String id) {

        return orderService.getOrder(id);
    }

    // Test:
    // GET http://localhost:8080/api/orders/fail
    //
    // This deliberately throws an exception.
    @GetMapping("/api/orders/fail")
    public String failOrder() {
        return orderService.failOrder();
    }

    // Test:
    // GET http://localhost:8080/api/orders/slow
    @GetMapping("/api/orders/slow")
    public String slowOperation() throws InterruptedException {
        return orderService.slowOperation();
    }


    @GetMapping("/api/orders/check-return-policy")
    public String checkReturnPolicy(@RequestParam String productType) throws InterruptedException {
        return orderService.checkReturnPolicyOfOrder(productType);
    }

    // Test:
    // GET http://localhost:8080/api/orders/self-invocation
    @GetMapping("/api/orders/self-invocation")
    public String selfInvocation() {
        return orderService.methodA();
    }
}

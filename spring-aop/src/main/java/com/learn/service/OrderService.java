package com.learn.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // Normal successful method.
    public String createOrder(String customer) {
        System.out.println(">>> Inside OrderService.createOrder()");
        return "Order created successfully for " + customer;
    }

    // Another successful method so you can see that the pointcut
    // can intercept multiple service methods.
    public String getOrder(String orderId) {
        System.out.println(">>> Inside OrderService.getOrder()");
        return "Order details for " + orderId;
    }

    // This method deliberately throws an exception.
    // Use it to observe @AfterThrowing and @After behavior.
    public String failOrder() {
        System.out.println(">>> Inside OrderService.failOrder()");
        throw new RuntimeException("Demo exception: order processing failed");
    }

    // Used to demonstrate @Around and execution-time measurement.
    public String slowOperation() throws InterruptedException {
        System.out.println(">>> Inside OrderService.slowOperation()");
        Thread.sleep(1000);
        return "Slow operation completed";
    }

    // Self-invocation demonstration.
    // Call /api/orders/self-invocation and observe that the internal
    // method call does not go through the Spring proxy again.
    public String methodA() {
        System.out.println(">>> Inside methodA()");
        return methodB();
    }

    public String methodB() {
        System.out.println(">>> Inside methodB()");
        return "methodB completed";
    }
}

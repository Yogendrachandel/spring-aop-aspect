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
    // method call does not go through the Spring proxy again -meaning non of advice will be executed for methodB() when called from methodA().
    public String methodA() {
        System.out.println(">>> Inside methodA()");
        return methodB();
    }

    public String methodB() {
        System.out.println(">>> Inside methodB()");
        return "methodB completed";
    }


    public String checkReturnPolicyOfOrder(String productType) throws InterruptedException {
    System.out.println(">>> Inside OrderService.checkReturnPolicyOfOrder()");

    if (productType == null || productType.trim().isEmpty()) {
        throw new RuntimeException("Product type is required");
    }

    String type = productType.trim().toLowerCase();

    boolean isInnerGarment = (type.contains("inner") && type.contains("garment"))
            || type.contains("innerwear")
            || type.contains("underwear")
            || type.contains("brief")
            || type.contains("lingerie");

    if (isInnerGarment) {
        throw new IllegalStateException("Return policy not applicable for inner garments: " + productType);
    }
    Thread.sleep(2000); // Simulate a slow operation

    return "Return policy for product " + productType + ": 30 days return policy.";
}
}

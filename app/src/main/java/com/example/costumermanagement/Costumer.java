package com.example.costumermanagement;

public class Costumer {
    private String name;
    private String status;
    private String paymentMethod;

    public Costumer(String name, String status, String paymentMethod) {
        this.name = name;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public Costumer() {
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}

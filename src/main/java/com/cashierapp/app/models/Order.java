package com.cashierapp.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private Map<String, Integer> products = new HashMap<>();
    private double totalAmount;
    private long timestamp;
    private boolean completed;
    private int orderNumber;

    // Getters and Setters
}

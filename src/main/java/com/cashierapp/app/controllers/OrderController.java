package com.cashierapp.app.controllers;

import com.cashierapp.app.models.Order;
import com.cashierapp.app.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOpenOrders() {
        return orderService.getAllOpenOrders();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PatchMapping("/{orderId}/products/{productId}")
    public void completeProduct(@PathVariable String orderId, @PathVariable String productId) {
        orderService.completeProductInOrder(orderId, productId);
    }

    @PatchMapping("/{orderId}/complete")
    public void completeOrder(@PathVariable String orderId) {
        orderService.completeOrder(orderId);
    }

    @GetMapping("/history")
    public List<Order> getAllOrderHistory() {
        return orderService.getAllOrderHistory();
    }

    @GetMapping("/history/search")
    public List<Order> findOrderHistoryByDateOrNumber(@RequestParam(required = false) String date, @RequestParam(required = false) Integer orderNumber) {
        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            long startTimestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
            long endTimestamp = startTimestamp + 86400000;
            return orderService.findOrderHistoryByDateRange(startTimestamp, endTimestamp);
        } else if (orderNumber != null) {
            return orderService.findOrderHistoryByOrderNumber(orderNumber);
        }
        return List.of();
    }
}

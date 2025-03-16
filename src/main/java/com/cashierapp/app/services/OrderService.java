package com.cashierapp.app.services;

import com.cashierapp.app.models.Order;
import com.cashierapp.app.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOpenOrders() {
        return orderRepository.findByCompleted(false);
    }

    public Order saveOrder(Order order) {
        Order latestOrder = orderRepository.findTopByOrderByOrderNumberDesc();
        int nextOrderNumber = latestOrder != null ? latestOrder.getOrderNumber() + 1 : 1;
        order.setOrderNumber(nextOrderNumber);
        order.setTimestamp(System.currentTimeMillis());
        order.setCompleted(false);
        return orderRepository.save(order);
    }

    public void completeProductInOrder(String orderId, String productId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            Map<String, Integer> products = order.getProducts();
            if (products.containsKey(productId)) {
                int quantity = products.get(productId);
                if (quantity > 1) {
                    products.put(productId, quantity - 1);
                } else {
                    products.remove(productId);
                }
            }
            if (products.isEmpty()) {
                order.setCompleted(true);
            }
            orderRepository.save(order);
        }
    }

    public void completeOrder(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setCompleted(true);
            orderRepository.save(order);
        }
    }

    public List<Order> getAllOrderHistory() {
        return orderRepository.findByCompleted(true);
    }

    public List<Order> findOrderHistoryByDateRange(long start, long end) {
        return orderRepository.findByTimestampBetween(start, end);
    }

    public List<Order> findOrderHistoryByOrderNumber(int orderNumber) { // Hinzugefügt
        return orderRepository.findByOrderNumber(orderNumber);
    }
}

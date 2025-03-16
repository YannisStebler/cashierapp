package com.cashierapp.app.repositories;

import com.cashierapp.app.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCompleted(boolean completed);
    List<Order> findByTimestampBetween(long start, long end);
    Order findTopByOrderByOrderNumberDesc();
    List<Order> findByOrderNumber(int orderNumber); 
}

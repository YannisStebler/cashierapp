package com.cashierapp.app.services;

import com.cashierapp.app.models.Order;
import com.cashierapp.app.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private OrderRepository orderRepository;

    public double getTotalSalesForPeriod(LocalDate startDate, LocalDate endDate) {
        long startTimestamp = startDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
        long endTimestamp = endDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
        List<Order> orders = orderRepository.findByTimestampBetween(startTimestamp, endTimestamp);
        return orders.stream().mapToDouble(Order::getTotalAmount).sum();
    }

    public double getTotalSalesForDay(LocalDate date) {
        LocalDate startOfDay = date;
        LocalDate endOfDay = date.plusDays(1);
        return getTotalSalesForPeriod(startOfDay, endOfDay);
    }

    public double getTotalSalesForWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(7);
        return getTotalSalesForPeriod(startOfWeek, endOfWeek);
    }

    public double getTotalSalesForMonth(LocalDate date) {
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = startOfMonth.plusDays(31);
        return getTotalSalesForPeriod(startOfMonth, endOfMonth);
    }
}

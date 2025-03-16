package com.cashierapp.app.controllers;

import com.cashierapp.app.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping("/daily")
    public double getDailySales(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return salesService.getTotalSalesForDay(localDate);
    }

    @GetMapping("/weekly")
    public double getWeeklySales(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return salesService.getTotalSalesForWeek(localDate);
    }

    @GetMapping("/monthly")
    public double getMonthlySales(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return salesService.getTotalSalesForMonth(localDate);
    }
}

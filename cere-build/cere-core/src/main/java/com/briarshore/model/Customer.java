package com.briarshore.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.briarshore.rule.conditions.Condition;

public class Customer {
    private int totalSales;
    private CustomerType type;
    private LocalDateTime recentSalesDate;

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(final int totalSales) {
        this.totalSales = totalSales;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(final CustomerType type) {
        this.type = type;
    }

    public LocalDateTime getRecentSalesDate() {
        return recentSalesDate;
    }

    public void setRecentSalesDate(final LocalDateTime recentSalesDate) {
        this.recentSalesDate = recentSalesDate;
    }
}

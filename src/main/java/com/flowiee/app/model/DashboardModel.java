package com.flowiee.app.model;

import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class DashboardModel {
    private String revenueToday;
    private String revenueThisMonth;
    private Integer ordersTodayQty;
    private List<Order> listOrdersToday;
    private Integer customersNewInMonthQty;
    private List<Customer> listCustomersNewInMonth;
    private LinkedHashMap<String, Float> revenueDayOfMonth;
    private LinkedHashMap<Integer, Float> revenueMonthOfYear;
    private LinkedHashMap<String, Float> revenueSalesChannel;
    private LinkedHashMap<String, Integer> productsTopSell;
}
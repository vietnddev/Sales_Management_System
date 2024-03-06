package com.flowiee.sms.model;

import com.flowiee.sms.entity.Customer;
import com.flowiee.sms.entity.Order;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class DashboardModel {
    private Integer totalProducts;
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
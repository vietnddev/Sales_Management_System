package com.flowiee.pms.model;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.model.dto.CustomerDTO;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class DashboardModel {
    private Integer totalProducts;
    private String revenueToday;
    private String revenueThisMonth;
    private Integer ordersNewTodayQty;
    private Integer ordersCancelTodayQty;
    private Integer ordersReturnTodayQty;
    private List<Order> listOrdersToday;
    private Integer customersNewInMonthQty;
    private Integer customersNewInTodayQty;
    private List<CustomerDTO> listCustomersNewInMonth;
    private LinkedHashMap<String, Float> revenueDayOfMonth;
    private LinkedHashMap<Integer, Float> revenueMonthOfYear;
    private LinkedHashMap<String, Float> revenueSalesChannel;
    private LinkedHashMap<String, Integer> productsTopSellQty;
    private LinkedHashMap<String, Integer> productsTopSellRevenue;
}
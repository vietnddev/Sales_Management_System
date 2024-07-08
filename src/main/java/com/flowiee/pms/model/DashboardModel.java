package com.flowiee.pms.model;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.model.dto.CustomerDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;
import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardModel {
    Integer totalProducts;
    String revenueToday;
    String revenueThisMonth;
    Integer ordersNewTodayQty;
    Integer ordersCancelTodayQty;
    Integer ordersReturnTodayQty;
    List<Order> listOrdersToday;
    Integer customersNewInMonthQty;
    Integer customersNewInTodayQty;
    List<CustomerDTO> listCustomersNewInMonth;
    LinkedHashMap<String, Float> revenueDayOfMonth;
    LinkedHashMap<Integer, Float> revenueMonthOfYear;
    LinkedHashMap<String, Float> revenueSalesChannel;
    LinkedHashMap<String, Integer> productsTopSellQty;
    LinkedHashMap<String, Integer> productsTopSellRevenue;
}
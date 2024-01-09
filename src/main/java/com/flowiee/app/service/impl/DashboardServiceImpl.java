package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.*;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.DashboardService;

import com.flowiee.app.service.OrderService;
import com.flowiee.app.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {
    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @Override
    @SuppressWarnings("unchecked")
    public DashboardModel loadDashboard() {
        logger.info("Start loadDashboard(): " + CommonUtil.now("YYYY/MM/dd HH:mm:ss"));

        //Revenue today
//      String revenueTodaySQL = "SELECT NVL(SUM(d.TOTAL_AMOUNT_AFTER_DISCOUNT), 0) FROM PRO_ORDER d WHERE TRUNC(d.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
//      logger.info("[getRevenueToday() - SQL findData]: " + revenueTodaySQL);
//      Query revenueTodayQuery = entityManager.createNativeQuery(revenueTodaySQL);
//      String revenueToday = CommonUtil.formatToVND(Float.parseFloat(String.valueOf(revenueTodayQuery.getSingleResult())));
//      entityManager.close();

        //Revenue this month
//      String revenueThisMonthSQL = "SELECT NVL(SUM(d.TOTAL_AMOUNT_AFTER_DISCOUNT), 0) FROM PRO_ORDER d WHERE EXTRACT(MONTH FROM d.THOI_GIAN_DAT_HANG) = EXTRACT(MONTH FROM SYSDATE)";
//      logger.info("[getRevenueThisMonth() - SQL findData]: " + revenueThisMonthSQL);
//      Query revenueThisMonthSQLQuery = entityManager.createNativeQuery(revenueThisMonthSQL);
//      String revenueThisMonth = CommonUtil.formatToVND(Float.parseFloat(String.valueOf(revenueThisMonthSQLQuery.getSingleResult())));
//      entityManager.close();

        //Customers new
//      String customersNewSQL = "SELECT * FROM PRO_CUSTOMER c WHERE EXTRACT(MONTH FROM c.CREATED_AT) = EXTRACT(MONTH FROM SYSDATE)";
//      logger.info("[getCustomersNew() - SQL findData]: " + customersNewSQL);
//      Query customersNewQuery = entityManager.createNativeQuery(customersNewSQL);
//      List<Customer> customersNew = customersNewQuery.getResultList();
//      entityManager.close();

        //Orders today
//      String ordersTodaySQL = "SELECT * FROM PRO_ORDER D WHERE TRUNC(D.THOI_GIAN_DAT_HANG) = TRUNC(SYSDATE)";
//      logger.info("[getOrdersToday() - SQL findData]: " + ordersTodaySQL);
//      Query ordersTodayQuery = entityManager.createNativeQuery(ordersTodaySQL);
//      List<Order> ordersToday = ordersTodayQuery.getResultList();
//      entityManager.close();

        //Products top sell
        String productsTopSellSQL = "SELECT * FROM " +
                                    "(SELECT s.VARIANT_NAME, NVL(SUM(d.SO_LUONG), 0) AS Total " +
                                    "FROM PRO_PRODUCT_VARIANT s " +
                                    "LEFT JOIN PRO_ORDER_DETAIL d ON s.id = d.PRODUCT_VARIANT_ID " +
                                    "GROUP BY s.ID, s.VARIANT_NAME " +
                                    "ORDER BY total DESC) " +
                                    "WHERE ROWNUM <= 10";
        logger.info("[getProductsTopSell() - SQL findData]: " + productsTopSellSQL);
        Query productsTopSellSQLQuery = entityManager.createNativeQuery(productsTopSellSQL);
        List<Object[]> productsTopSellResultList = productsTopSellSQLQuery.getResultList();
        LinkedHashMap<String, Integer> productsTopSell = new LinkedHashMap<>();
        for (Object[] data : productsTopSellResultList) {
            productsTopSell.put(String.valueOf(data[0]), Integer.parseInt(String.valueOf(data[1])));
        }
        entityManager.close();

        //Revenue month of year
        String revenueMonthOfYearSQL = "SELECT SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 1 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS JAN, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 2 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS FEB, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 3 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS MAR, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 4 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS APRIL, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 5 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS MAY, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 6 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS JUN, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 7 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS JUL, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 8 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS AUG," +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 9 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS SEP, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 10 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS OCT, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 11 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS NOV, " +
                                       "SUM(CASE WHEN EXTRACT(MONTH FROM d.ORDER_TIME) = 12 THEN d.TOTAL_AMOUNT_AFTER_DISCOUNT ELSE 0 END) AS DEC " +
                                       "FROM PRO_ORDER d " +
                                       "WHERE EXTRACT(YEAR FROM d.THOI_GIAN_DAT_HANG) = EXTRACT(YEAR FROM SYSDATE)";
        logger.info("[getRevenueMonthOfYearSQL() - SQL findData]: " + revenueMonthOfYearSQL);
        Query revenueMonthOfYearSQLQuery = entityManager.createNativeQuery(revenueMonthOfYearSQL);
        List<Object[]> revenueMonthOfYearSQLResultList = revenueMonthOfYearSQLQuery.getResultList();
        LinkedHashMap<Integer, Float> revenueMonthOfYear = new LinkedHashMap<>();
        for (int i = 0; i < revenueMonthOfYearSQLResultList.get(0).length; i++) {
            revenueMonthOfYear.put(i + 1, Float.parseFloat(String.valueOf(revenueMonthOfYearSQLResultList.get(0)[i] != null ? revenueMonthOfYearSQLResultList.get(0)[i] : 0)));
        }
        entityManager.close();

        //Revenue day of month
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        String revenueDayOfMonthSQL = "WITH all_dates AS " +
                                      "( " +
                                      "SELECT TO_DATE('" + firstDay + "', 'YYYY-MM-DD') + LEVEL - 1 AS DAY " +
                                      "FROM DUAL CONNECT BY LEVEL <= TO_DATE('" + lastDay + "', 'YYYY-MM-DD') - TO_DATE('" + firstDay + "', 'YYYY-MM-DD') + 1" +
                                      ") " +
                                      "SELECT all_dates.DAY, NVL(SUM(PRO_ORDER.TOTAL_AMOUNT_AFTER_DISCOUNT), 0) AS REVENUE " +
                                      "FROM all_dates " +
                                      "LEFT JOIN PRO_ORDER ON TRUNC(PRO_ORDER.ORDER_TIME) = all_dates.DAY " +
                                      "GROUP BY all_dates.DAY " +
                                      "ORDER BY all_dates.DAY";
        logger.info("[getRevenueDayOfMonth() - SQL findData]: " + revenueDayOfMonthSQL);
        Query revenueDayOfMonthSQLQuery = entityManager.createNativeQuery(revenueDayOfMonthSQL);
        List<Object[]> revenueDayOfMonthSQLResultList = revenueDayOfMonthSQLQuery.getResultList();
        LinkedHashMap<String, Float> revenueDayOfMonth = new LinkedHashMap<>();
        for (int i = 0; i < revenueDayOfMonthSQLResultList.size(); i++) {
            revenueDayOfMonth.put("Day " + (i + 1), Float.parseFloat(String.valueOf(revenueDayOfMonthSQLResultList.get(i)[1])));
        }
        entityManager.close();

        //Revenue by sales channel
        String revenueBySalesChannelSQL = "SELECT c.NAME, c.COLOR, NVL(SUM(d.TOTAL_AMOUNT_AFTER_DISCOUNT),0) AS TOTAL " +
                                          "FROM (SELECT * FROM CATEGORY WHERE TYPE = 'SALES_CHANNEL') c " +
                                          "LEFT JOIN PRO_ORDER d ON c.ID = d.KENH_BAN_HANG " +
                                          "GROUP BY c.NAME, c.COLOR";
        logger.info("[getRevenueBySalesChannel() - SQL findData]: " + revenueBySalesChannelSQL);
        Query revenueBySalesChannelQuery = entityManager.createNativeQuery(revenueBySalesChannelSQL);
        List<Object[]> revenueBySalesChannelResultList = revenueBySalesChannelQuery.getResultList();
        LinkedHashMap<String, Float> revenueSalesChannel = new LinkedHashMap<>();
        for (Object[] data : revenueBySalesChannelResultList) {
            revenueSalesChannel.put(String.valueOf(data[0]), Float.parseFloat(String.valueOf(data[2])));
        }
        entityManager.close();

        String revenueToday = CommonUtil.formatToVND(orderService.findRevenueToday());
        String revenueThisMonth = CommonUtil.formatToVND(orderService.findRevenueThisMonth());
        List<Customer> customersNew = customerService.findCustomerNewInMonth();
        List<Order> ordersToday = orderService.findOrdersToday();

        DashboardModel dashboard = new DashboardModel();
        dashboard.setRevenueToday(revenueToday);
        dashboard.setRevenueThisMonth(revenueThisMonth);
        dashboard.setOrdersTodayQty(ordersToday.size());
        dashboard.setListOrdersToday(ordersToday);
        dashboard.setCustomersNewInMonthQty(customersNew.size());
        dashboard.setListCustomersNewInMonth(customersNew);
        dashboard.setRevenueDayOfMonth(revenueDayOfMonth);
        dashboard.setRevenueMonthOfYear(revenueMonthOfYear);
        dashboard.setRevenueSalesChannel(revenueSalesChannel);
        dashboard.setProductsTopSell(productsTopSell);

        logger.info("Finished loadDashboard(): " + CommonUtil.now("YYYY/MM/dd HH:mm:ss"));
        return dashboard;
    }
}
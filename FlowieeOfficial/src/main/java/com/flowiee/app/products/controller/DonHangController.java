package com.flowiee.app.products.controller;

import com.flowiee.app.products.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/sales/orders")
public class DonHangController {
    @Autowired
    DonHangService ordersService;

    @GetMapping("/status={status}")
    public String getOrdersByStatus(@PathVariable String status, ModelMap modelMap){
        /*
         * Lấy ra danh sách đơn đặt hàng theo trạng thái được truyền vào
         * Status = 0: Lấy toàn bộ danh sách bao gồm tất cả trạng thái
         * Status = 1: Lấy danh sách đơn hàng chờ shop xác nhận
         * Status = 2: Lấy danh sách đơn hàng đang giao
         * Status = 3: Lấy danh sách đơn hàng đã hoàn thành
         * Status = 4: Lấy danh sách đơn hàng đã bị hủy
         * */
        switch (status) {
            case "0" :
                modelMap.addAttribute("listOrders", ordersService.getAllOrders());
                modelMap.addAttribute("status", "DANH SÁCH TẤT CẢ ĐƠN ĐẶT HÀNG");
                System.out.println(ordersService.getAllOrders());
                break;
            case "1" :
                modelMap.addAttribute("listOrders", ordersService.getByStatus("1"));
                modelMap.addAttribute("status", "DANH SÁCH ĐƠN HÀNG CHỜ XÁC NHẬN");
                System.out.println(ordersService.getByStatus("1"));
                break;
            case "2" :
                modelMap.addAttribute("listOrders", ordersService.getByStatus("2"));
                modelMap.addAttribute("status", "DANH SÁCH ĐƠN HÀNG ĐANG GIAO");
                System.out.println(ordersService.getByStatus("2"));
                break;
            case "3" :
                modelMap.addAttribute("listOrders", ordersService.getByStatus("3"));
                modelMap.addAttribute("status", "DANH SÁCH ĐƠN HÀNG ĐÃ HOÀN THÀNH");
                System.out.println(ordersService.getByStatus("3"));
                break;
            case "4" :
                modelMap.addAttribute("listOrders", ordersService.getByStatus("4"));
                modelMap.addAttribute("status", "DANH SÁCH ĐƠN HÀNG ĐÃ BỊ HỦY");
                System.out.println(ordersService.getByStatus("4"));
                break;
        }
        return "pages/sales/orders";
    }
}

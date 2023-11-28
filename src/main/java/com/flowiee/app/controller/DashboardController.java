package com.flowiee.app.controller;

import com.flowiee.app.model.DashboardModel;
import com.flowiee.app.security.author.ValidateModuleProduct;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RequestMapping("")
public class DashboardController extends BaseController {
    @Autowired
    private ValidateModuleProduct validateModuleProduct;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ModelAndView reportDoanhThu() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleProduct.readDashboard()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_DASHBOARD);

            DashboardModel dashboard = dashboardService.loadDashboard();

            //Số lượng đơn hàng hôm nay
            modelAndView.addObject("soLuongDonHangHomNay", dashboard.getOrdersTodayQty());
            modelAndView.addObject("danhSachDonHangHomNay", dashboard.getListOrdersToday());
            //Doanh thu hôm nay
            modelAndView.addObject("doanhThuHomNay", dashboard.getRevenueToday());
            //Doanh thu trong tháng này
            modelAndView.addObject("doanhThuThangNay", dashboard.getRevenueThisMonth());
            //Số lượng khách hàng mới trong tháng
            modelAndView.addObject("khachHangMoiTrongThang", dashboard.getCustomersNewInMonthQty());
            modelAndView.addObject("danhSachkhachHangMoiTrongThang", dashboard.getListCustomersNewInMonth());

            //Pie chart - Doanh thu theo kênh bán hàng
            modelAndView.addObject("doanhThuOfKBH_listTen", dashboard.getRevenueSalesChannel().keySet());
            modelAndView.addObject("doanhThuOfKBH_listDoanhThu", dashboard.getRevenueSalesChannel().values());
            modelAndView.addObject("doanhThuOfKBH_listMauSac", null);

            //Line chart - Doanh thu các tháng theo năm
            modelAndView.addObject("doanhThuOfMonth_listDoanhThu", dashboard.getRevenueMonthOfYear().values());

            //Line chart - Doanh thu các ngày theo tháng
            modelAndView.addObject("doanhThuOfDay_listNgay", dashboard.getRevenueDayOfMonth().keySet());
            modelAndView.addObject("doanhThuOfDay_listDoanhThu", dashboard.getRevenueDayOfMonth().values());

            //Bar chart - Top sản phẩm bán chạy
            modelAndView.addObject("topSanPham_listTenSanPham", dashboard.getProductsTopSell().keySet());
            modelAndView.addObject("topSanPham_listSoLuong", dashboard.getProductsTopSell().values());

            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
    }
}
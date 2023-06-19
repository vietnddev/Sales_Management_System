package com.flowiee.app.dashboard.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDashboard;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.common.utils.CurrencyUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.dashboard.model.DoanhThuTheoKenhBanHang;
import com.flowiee.app.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("")
public class DashboardController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModuleDashboard kiemTraQuyenModuleDashboard;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String reportDoanhThu(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleDashboard.kiemTraQuyenXem()) {
            //Số lượng đơn hàng hôm nay
            modelMap.addAttribute("soLuongDonHangHomNay", dashboardService.getSoLuongDonHangHomNay());
            //Doanh thu hôm nay
            modelMap.addAttribute("doanhThuHomNay", CurrencyUtil.formatToVND(dashboardService.getDoanhThuHomNay()));
            //Doanh thu trong tháng này
            modelMap.addAttribute("doanhThuThangNay", CurrencyUtil.formatToVND(dashboardService.getDoanhThuThangNay()));
            //Số lượng khách hàng mới trong tháng
            modelMap.addAttribute("khachHangMoiTrongThang", dashboardService.getSoLuongKhachHangMoi());

            //Pie chart - Doanh thu theo kênh bán hàng
            DoanhThuTheoKenhBanHang doanhThuTheoKenhBanHang = dashboardService.getDoanhThuTheoKenhBanHang();
            modelMap.addAttribute("doanhThuOfKBH_listTen", doanhThuTheoKenhBanHang.getTenOfKenh());
            modelMap.addAttribute("doanhThuOfKBH_listDoanhThu", doanhThuTheoKenhBanHang.getDoanhThuOfKenh());
            modelMap.addAttribute("doanhThuOfKBH_listMauSac", doanhThuTheoKenhBanHang.getMauSac());
            return PagesUtil.PAGE_DASHBOARD;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}
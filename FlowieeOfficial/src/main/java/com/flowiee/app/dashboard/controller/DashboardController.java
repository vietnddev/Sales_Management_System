package com.flowiee.app.dashboard.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDashboard;
import com.flowiee.app.common.utils.CurrencyUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.dashboard.model.DoanhThuCacNgayTheoThang;
import com.flowiee.app.dashboard.model.DoanhThuCacThangTheoNam;
import com.flowiee.app.dashboard.model.TopSanPhamBanChay;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.dashboard.model.DoanhThuTheoKenhBanHang;
import com.flowiee.app.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView reportDoanhThu() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDashboard.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DASHBOARD);
                        
            //Số lượng đơn hàng hôm nay
            modelAndView.addObject("soLuongDonHangHomNay", dashboardService.getSoLuongDonHangHomNay());
            //Doanh thu hôm nay
            modelAndView.addObject("doanhThuHomNay", CurrencyUtil.formatToVND(dashboardService.getDoanhThuHomNay()));
            //Doanh thu trong tháng này
            modelAndView.addObject("doanhThuThangNay", CurrencyUtil.formatToVND(dashboardService.getDoanhThuThangNay()));
            //Số lượng khách hàng mới trong tháng
            modelAndView.addObject("khachHangMoiTrongThang", dashboardService.getSoLuongKhachHangMoi());

            //Pie chart - Doanh thu theo kênh bán hàng
            DoanhThuTheoKenhBanHang doanhThuTheoKenhBanHang = dashboardService.getDoanhThuTheoKenhBanHang();
            modelAndView.addObject("doanhThuOfKBH_listTen", doanhThuTheoKenhBanHang.getTenOfKenh());
            modelAndView.addObject("doanhThuOfKBH_listDoanhThu", doanhThuTheoKenhBanHang.getDoanhThuOfKenh());
            modelAndView.addObject("doanhThuOfKBH_listMauSac", doanhThuTheoKenhBanHang.getMauSac());

            //Line chart - Doanh thu các tháng theo năm
            DoanhThuCacThangTheoNam doanhThuCacThangTheoNam = dashboardService.getDoanhThuCacThangTheoNam();
            modelAndView.addObject("doanhThuOfMonth_listDoanhThu", doanhThuCacThangTheoNam.getDoanhThu());

            //Line chart - Doanh thu các ngày theo tháng
            DoanhThuCacNgayTheoThang doanhThuCacNgayTheoThang = dashboardService.getDoanhThuCacNgayTheoThang();
            modelAndView.addObject("doanhThuOfDay_listNgay", doanhThuCacNgayTheoThang.getListNgay());
            modelAndView.addObject("doanhThuOfDay_listDoanhThu", doanhThuCacNgayTheoThang.getListDoanhThu());

            //Bar chart - Top sản phẩm bán chạy
            TopSanPhamBanChay topSanPhamBanChay = dashboardService.getTopSanPhamBanChay();
            modelAndView.addObject("topSanPham_listTenSanPham", topSanPhamBanChay.getTenSanPham());
            modelAndView.addObject("topSanPham_listSoLuong", topSanPhamBanChay.getSoLuongDaBan());

            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}
package com.flowiee.app.product.controller;

import com.flowiee.app.config.KiemTraQuyenModuleDashboard;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.CurrencyUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.product.model.DoanhThuCacNgayTheoThang;
import com.flowiee.app.product.model.DoanhThuCacThangTheoNam;
import com.flowiee.app.product.model.TopSanPhamBanChay;
import com.flowiee.app.product.entity.Customer;
import com.flowiee.app.product.entity.Order;
import com.flowiee.app.product.model.DoanhThuTheoKenhBanHang;
import com.flowiee.app.product.services.DashboardService;
import com.flowiee.app.service.system.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("")
public class DashboardController extends BaseController {
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
            List<Order> listOrderToDay = dashboardService.getSoLuongDonHangHomNay();
            modelAndView.addObject("soLuongDonHangHomNay", listOrderToDay.size());
            modelAndView.addObject("danhSachDonHangHomNay", listOrderToDay);
            //Doanh thu hôm nay
            modelAndView.addObject("doanhThuHomNay", CurrencyUtil.formatToVND(dashboardService.getDoanhThuHomNay()));
            //Doanh thu trong tháng này
            modelAndView.addObject("doanhThuThangNay", CurrencyUtil.formatToVND(dashboardService.getDoanhThuThangNay()));
            //Số lượng khách hàng mới trong tháng
            List<Customer> listCustomerThisMonth = dashboardService.getSoLuongKhachHangMoi();
            modelAndView.addObject("khachHangMoiTrongThang", listCustomerThisMonth.size());
            modelAndView.addObject("danhSachkhachHangMoiTrongThang", listCustomerThisMonth);

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

            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}
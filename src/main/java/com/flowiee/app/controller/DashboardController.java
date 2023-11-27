package com.flowiee.app.controller;

import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.security.author.ValidateModuleDashboard;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.DoanhThuCacNgayTheoThangModel;
import com.flowiee.app.model.DoanhThuCacThangTheoNamModel;
import com.flowiee.app.model.DoanhThuTheoKenhBanHangModel;
import com.flowiee.app.model.TopBestSellerModel;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.service.DashboardService;
import com.flowiee.app.service.AccountService;

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
    private ValidateModuleDashboard validateModuleDashboard;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ModelAndView reportDoanhThu() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleDashboard.readDashboard()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_DASHBOARD);
                        
            //Số lượng đơn hàng hôm nay
            List<Order> listOrderToDay = dashboardService.getSoLuongDonHangHomNay();
            modelAndView.addObject("soLuongDonHangHomNay", listOrderToDay.size());
            modelAndView.addObject("danhSachDonHangHomNay", listOrderToDay);
            //Doanh thu hôm nay
            modelAndView.addObject("doanhThuHomNay", FlowieeUtil.formatToVND(dashboardService.getDoanhThuHomNay()));
            //Doanh thu trong tháng này
            modelAndView.addObject("doanhThuThangNay", FlowieeUtil.formatToVND(dashboardService.getDoanhThuThangNay()));
            //Số lượng khách hàng mới trong tháng
            List<Customer> listCustomerThisMonth = dashboardService.getSoLuongKhachHangMoi();
            modelAndView.addObject("khachHangMoiTrongThang", listCustomerThisMonth.size());
            modelAndView.addObject("danhSachkhachHangMoiTrongThang", listCustomerThisMonth);

            //Pie chart - Doanh thu theo kênh bán hàng
            DoanhThuTheoKenhBanHangModel doanhThuTheoKenhBanHangModel = dashboardService.getDoanhThuTheoKenhBanHang();
            modelAndView.addObject("doanhThuOfKBH_listTen", doanhThuTheoKenhBanHangModel.getTenOfKenh());
            modelAndView.addObject("doanhThuOfKBH_listDoanhThu", doanhThuTheoKenhBanHangModel.getDoanhThuOfKenh());
            modelAndView.addObject("doanhThuOfKBH_listMauSac", doanhThuTheoKenhBanHangModel.getMauSac());

            //Line chart - Doanh thu các tháng theo năm
            DoanhThuCacThangTheoNamModel doanhThuCacThangTheoNamModel = dashboardService.getDoanhThuCacThangTheoNam();
            modelAndView.addObject("doanhThuOfMonth_listDoanhThu", doanhThuCacThangTheoNamModel.getDoanhThu());

            //Line chart - Doanh thu các ngày theo tháng
            DoanhThuCacNgayTheoThangModel doanhThuCacNgayTheoThangModel = dashboardService.getDoanhThuCacNgayTheoThang();
            modelAndView.addObject("doanhThuOfDay_listNgay", doanhThuCacNgayTheoThangModel.getListNgay());
            modelAndView.addObject("doanhThuOfDay_listDoanhThu", doanhThuCacNgayTheoThangModel.getListDoanhThu());

            //Bar chart - Top sản phẩm bán chạy
            TopBestSellerModel topBestSellerModel = dashboardService.getTopSanPhamBanChay();
            modelAndView.addObject("topSanPham_listTenSanPham", topBestSellerModel.getTenSanPham());
            modelAndView.addObject("topSanPham_listSoLuong", topBestSellerModel.getSoLuongDaBan());

            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
    }
}
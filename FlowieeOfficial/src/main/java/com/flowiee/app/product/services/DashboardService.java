package com.flowiee.app.product.services;

import com.flowiee.app.product.model.DoanhThuCacNgayTheoThang;
import com.flowiee.app.product.model.DoanhThuCacThangTheoNam;
import com.flowiee.app.product.model.DoanhThuTheoKenhBanHang;
import com.flowiee.app.product.model.TopSanPhamBanChay;
import com.flowiee.app.product.entity.Order;
import com.flowiee.app.product.entity.Customer;

import java.util.List;

public interface DashboardService {
    DoanhThuTheoKenhBanHang getDoanhThuTheoKenhBanHang();

    DoanhThuCacThangTheoNam getDoanhThuCacThangTheoNam();

    DoanhThuCacNgayTheoThang getDoanhThuCacNgayTheoThang();

    TopSanPhamBanChay getTopSanPhamBanChay();

    List<Order> getSoLuongDonHangHomNay();

    Float getDoanhThuHomNay();

    Float getDoanhThuThangNay();

    List<Customer> getSoLuongKhachHangMoi();
}
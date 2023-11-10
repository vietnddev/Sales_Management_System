package com.flowiee.app.service.product;

import com.flowiee.app.entity.product.Customer;
import com.flowiee.app.entity.product.Order;
import com.flowiee.app.model.product.DoanhThuCacNgayTheoThang;
import com.flowiee.app.model.product.DoanhThuCacThangTheoNam;
import com.flowiee.app.model.product.DoanhThuTheoKenhBanHang;
import com.flowiee.app.model.product.TopSanPhamBanChay;

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
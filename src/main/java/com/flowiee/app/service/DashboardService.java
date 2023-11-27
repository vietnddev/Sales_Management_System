package com.flowiee.app.service;

import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.DoanhThuCacNgayTheoThangModel;
import com.flowiee.app.model.DoanhThuCacThangTheoNamModel;
import com.flowiee.app.model.DoanhThuTheoKenhBanHangModel;
import com.flowiee.app.model.TopBestSellerModel;

import java.util.List;

public interface DashboardService {
    DoanhThuTheoKenhBanHangModel getDoanhThuTheoKenhBanHang();

    DoanhThuCacThangTheoNamModel getDoanhThuCacThangTheoNam();

    DoanhThuCacNgayTheoThangModel getDoanhThuCacNgayTheoThang();

    TopBestSellerModel getTopSanPhamBanChay();

    List<Order> getSoLuongDonHangHomNay();

    Float getDoanhThuHomNay();

    Float getDoanhThuThangNay();

    List<Customer> getSoLuongKhachHangMoi();
}
package com.flowiee.app.dashboard.service;

import com.flowiee.app.dashboard.model.DoanhThuTheoKenhBanHang;

public interface DashboardService {
    DoanhThuTheoKenhBanHang getDoanhThuTheoKenhBanHang();

    Integer getSoLuongDonHangHomNay();

    Float getDoanhThuHomNay();

    Float getDoanhThuThangNay();

    Integer getSoLuongKhachHangMoi();
}
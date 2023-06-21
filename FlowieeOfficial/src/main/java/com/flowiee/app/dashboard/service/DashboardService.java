package com.flowiee.app.dashboard.service;

import com.flowiee.app.dashboard.model.DoanhThuCacThangTheoNam;
import com.flowiee.app.dashboard.model.DoanhThuTheoKenhBanHang;
import com.flowiee.app.dashboard.model.TopSanPhamBanChay;

public interface DashboardService {
    DoanhThuTheoKenhBanHang getDoanhThuTheoKenhBanHang();

    DoanhThuCacThangTheoNam getDoanhThuCacThangTheoNam();

    TopSanPhamBanChay getTopSanPhamBanChay();

    Integer getSoLuongDonHangHomNay();

    Float getDoanhThuHomNay();

    Float getDoanhThuThangNay();

    Integer getSoLuongKhachHangMoi();
}
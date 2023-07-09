package com.flowiee.app.dashboard.service;

import com.flowiee.app.dashboard.model.DoanhThuCacNgayTheoThang;
import com.flowiee.app.dashboard.model.DoanhThuCacThangTheoNam;
import com.flowiee.app.dashboard.model.DoanhThuTheoKenhBanHang;
import com.flowiee.app.dashboard.model.TopSanPhamBanChay;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.KhachHang;

import java.util.List;

public interface DashboardService {
    DoanhThuTheoKenhBanHang getDoanhThuTheoKenhBanHang();

    DoanhThuCacThangTheoNam getDoanhThuCacThangTheoNam();

    DoanhThuCacNgayTheoThang getDoanhThuCacNgayTheoThang();

    TopSanPhamBanChay getTopSanPhamBanChay();

    List<DonHang> getSoLuongDonHangHomNay();

    Float getDoanhThuHomNay();

    Float getDoanhThuThangNay();

    List<KhachHang> getSoLuongKhachHangMoi();
}
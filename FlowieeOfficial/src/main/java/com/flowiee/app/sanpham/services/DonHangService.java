package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DonHangService extends BaseService<DonHang> {

    List<DonHang> findAll(String searchTxt, String thoiGianDatHang, int kenhBanHangId, int trangThaiDonHangId);

    List<DonHang> findByTrangThai(int trangThaiDonHangId);

    List<DonHang> search();

    List<DonHang> findByKhachHangId(int id);

    List<DonHang> findByNhanVienId(int accountId);

    ResponseEntity<?> exportDanhSachDonHang();
}
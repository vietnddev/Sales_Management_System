package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.DonHangThanhToan;

import java.util.List;

public interface DonHangThanhToanService extends BaseService<DonHangThanhToan> {
    List<DonHangThanhToan> findByDonHangId(int id);
}
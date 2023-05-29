package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.DonHang;

import java.util.List;

public interface DonHangService {

    List<DonHang> getAllOrders();

    List<DonHang> getByStatus(String status);
}
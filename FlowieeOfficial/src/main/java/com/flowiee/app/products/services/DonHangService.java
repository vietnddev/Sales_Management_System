package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.DonHang;

import java.util.List;

public interface DonHangService {

    List<DonHang> getAllOrders();

    List<DonHang> getByStatus(String status);
}
package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.DonHangChiTiet;

import java.util.List;

public interface ChiTietDonHangService {

    List<DonHangChiTiet> getDetailByOrdersID(int ordersID);

}
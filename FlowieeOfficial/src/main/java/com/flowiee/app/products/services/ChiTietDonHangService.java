package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.DonHangChiTiet;

import java.util.List;

public interface ChiTietDonHangService {

    List<DonHangChiTiet> getDetailByOrdersID(int ordersID);

}
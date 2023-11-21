package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.product.OrderRequest;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    List<Order> findAll(String searchTxt, String thoiGianDatHang, int kenhBanHangId, int trangThaiDonHangId);

    List<Order> findByTrangThai(int trangThaiDonHangId);

    List<Order> search();

    List<Order> findByKhachHangId(int id);

    List<Order> findByNhanVienId(int accountId);

    ResponseEntity<?> exportDanhSachDonHang();

    String save(OrderRequest orderRequest);

    List<Order> findBySalesChannel(Integer salesChannelId);

    List<Order> findByOrderStatus(Integer orderStatusId);
}
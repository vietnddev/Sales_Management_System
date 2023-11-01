package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.Order;
import com.flowiee.app.product.model.OrderRequest;
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
}
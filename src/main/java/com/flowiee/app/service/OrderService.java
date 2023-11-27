package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.Order;
import com.flowiee.app.model.request.OrderRequest;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface OrderService extends BaseService<Order> {
    public List<OrderDTO> findAll(OrderDTO orderDTO);

    List<Order> findByTrangThai(int trangThaiDonHangId);

    List<Order> findByKhachHangId(int id);

    List<Order> findByNhanVienId(int accountId);

    ResponseEntity<?> exportDanhSachDonHang();

    String save(OrderRequest orderRequest);

    List<Order> findBySalesChannel(Integer salesChannelId);

    List<Order> findByOrderStatus(Integer orderStatusId);
}
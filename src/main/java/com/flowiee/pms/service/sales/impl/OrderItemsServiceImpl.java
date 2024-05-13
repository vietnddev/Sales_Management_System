package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.repository.sales.OrderDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.OrderItemsService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemsServiceImpl extends BaseService implements OrderItemsService {
    @Autowired
    private OrderDetailRepository orderDetailRepo;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepo.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Integer orderDetailId) {
        return orderDetailRepo.findById(orderDetailId);
    }

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailRepo.findByOrderId(orderId);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        try {
            OrderDetail orderDetailSaved = orderDetailRepo.save(orderDetail);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_ORD_C.name(), "Thêm mới item vào đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Thêm mới item vào đơn hàng " + orderDetail.toString());
            return orderDetailSaved;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public OrderDetail update(OrderDetail orderDetail, Integer orderDetailId) {
        try {
            orderDetail.setId(orderDetailId);
            OrderDetail orderItemUpdated = orderDetailRepo.save(orderDetail);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_ORD_U.name(), "Cập nhật item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Cập nhật item of đơn hàng " + orderDetail.toString());
            return orderItemUpdated;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public String delete(Integer orderDetailId) {
        Optional<OrderDetail> orderDetail = this.findById(orderDetailId);
        if (orderDetail.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            orderDetailRepo.deleteById(orderDetailId);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_ORD_D.name(), "Xóa item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Xóa item of đơn hàng " + orderDetail.toString());
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }
}
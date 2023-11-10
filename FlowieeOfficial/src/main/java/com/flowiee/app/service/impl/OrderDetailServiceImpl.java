package com.flowiee.app.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.action.DonHangAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.product.OrderDetail;
import com.flowiee.app.repository.product.OrderDetailRepository;
import com.flowiee.app.service.product.OrderDetailService;
import com.flowiee.app.service.product.OrderService;
import com.flowiee.app.service.system.SystemLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail findById(Integer id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderDetail> findByDonHangId(Integer donHangId) {
        if (donHangId <= 0 || orderService.findById(donHangId) == null) {
            throw new NotFoundException();
        }
        return orderDetailRepository.findByDonHangId(orderService.findById(donHangId));
    }

    @Override
    @Transactional
    public String save(OrderDetail orderDetail) {
        if (orderDetail.getOrder() == null) {
            throw new NotFoundException();
        }
        try {
            orderDetailRepository.save(orderDetail);
            systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Thêm mới item vào đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Thêm mới item vào đơn hàng " + orderDetail.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(OrderDetail orderDetail, Integer id) {
        if (id <=0 || this.findById(id) == null || orderDetail.getOrder() == null) {
            throw new NotFoundException();
        }
        try {
            orderDetail.setId(id);
            orderDetailRepository.save(orderDetail);
            systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Cập nhật item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Cập nhật item of đơn hàng " + orderDetail.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer id) {
        OrderDetail orderDetail = this.findById(id);
        if (id <= 0 || orderDetail == null) {
            throw new NotFoundException();
        }
        try {
            orderDetailRepository.deleteById(id);
            systemLogService.writeLog(module, DonHangAction.UPDATE_DONHANG.name(), "Xóa item of đơn hàng: " + orderDetail.toString());
            logger.info(OrderServiceImpl.class.getName() + ": Xóa item of đơn hàng " + orderDetail.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }
}
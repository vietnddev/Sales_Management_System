package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.sales.OrderDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.OrderHistoryService;
import com.flowiee.pms.service.sales.OrderItemsService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.LogUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderItemsServiceImpl extends BaseService implements OrderItemsService {
    private static final String mainObjectName = "OrderItems";

    @Autowired
    private OrderDetailRepository orderDetailRepo;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private OrderHistoryService orderHistoryService;

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
            systemLogService.writeLogCreate(MODULE.PRODUCT.name(), ACTION.PRO_ORD_C.name(), mainObjectName, "Thêm mới item vào đơn hàng", orderDetail.toString());
            logger.info("{}: Thêm mới item vào đơn hàng {}", OrderServiceImpl.class.getName(), orderDetail.toString());
            return orderDetailSaved;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public OrderDetail update(OrderDetail orderDetail, Integer orderDetailId) {
        try {
            Optional<OrderDetail> orderDetailOptional = this.findById(orderDetailId);
            if (orderDetailOptional.isEmpty()) {
                throw new BadRequestException();
            }
            OrderDetail orderItemBefore = ObjectUtils.clone(orderDetailOptional.get());

            orderDetail.setId(orderDetailId);
            OrderDetail orderItemUpdated = orderDetailRepo.save(orderDetail);

            String logTitle = "Cập nhật đơn hàng " + orderItemUpdated.getOrder().getCode();
            Map<String, Object[]> logChanges = LogUtils.logChanges(orderItemBefore, orderItemUpdated);
            orderHistoryService.save(logChanges, logTitle, orderItemBefore.getOrder().getId(), orderItemBefore.getId());
            systemLogService.writeLogUpdate(MODULE.PRODUCT.name(), ACTION.PRO_ORD_U.name(), mainObjectName, logTitle, logChanges);
            logger.info("{}: Cập nhật item of đơn hàng {}", OrderServiceImpl.class.getName(), orderItemUpdated.toString());

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
            systemLogService.writeLogDelete(MODULE.PRODUCT.name(), ACTION.PRO_ORD_D.name(), mainObjectName, "Xóa item of đơn hàng", orderDetail.toString());
            logger.info("{}: Xóa item of đơn hàng {}", OrderServiceImpl.class.getName(), orderDetail.toString());
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }
}
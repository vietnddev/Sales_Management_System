package com.flowiee.pms.service;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.entity.sales.VoucherTicket;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.CoreUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SystemLogService systemLogService;

    protected Pageable getPageable(int pageNum, int pageSize) {
        return getPageable(pageNum, pageSize, null);
    }

    protected Pageable getPageable(int pageNum, int pageSize, Sort sort) {
        if (pageSize >= 0 && pageNum >= 0) {
            if (sort == null) {
                return PageRequest.of(pageNum, pageSize);
            }
            return PageRequest.of(pageNum, pageSize, sort);
        }
        return Pageable.unpaged();
    }

    public static boolean isConfigAvailable(SystemConfig pSystemConfig) {
        if (pSystemConfig == null || CoreUtils.isNullStr(pSystemConfig.getValue())) {
            return false;
        }
        return true;
    }

    @Data
    protected class VldModel {
        private Account       salesAssistant;
        private Category      paymentMethod;
        private Category      brand;
        private Category      color;
        private Category      fabricType;
        private Category      size;
        private Category      salesChannel;
        private Category      unit;
        private Category      productType;
        private Customer      customer;
        private OrderCart     orderCart;
        private VoucherTicket voucherTicket;
    }
}
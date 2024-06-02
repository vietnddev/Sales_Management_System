package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.LedgerPayment;
import com.flowiee.pms.service.CrudService;
import org.springframework.data.domain.Page;

public interface LedgerPaymentService extends CrudService<LedgerPayment> {
    Page<LedgerPayment> findAll(int pageSize, int pageNum);
}
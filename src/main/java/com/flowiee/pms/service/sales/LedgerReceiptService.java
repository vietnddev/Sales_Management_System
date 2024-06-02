package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.LedgerReceipt;
import com.flowiee.pms.service.CrudService;
import org.springframework.data.domain.Page;

public interface LedgerReceiptService extends CrudService<LedgerReceipt> {
    Page<LedgerReceipt> findAll(int pageSize, int pageNum);
}
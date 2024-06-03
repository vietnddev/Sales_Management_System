package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.LedgerTransaction;
import com.flowiee.pms.service.BaseCurd;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface LedgerTransactionService extends BaseCurd<LedgerTransaction> {
    Page<LedgerTransaction> findAll(int pageSize, int pageNum, LocalDate fromDate, LocalDate toDate);
}
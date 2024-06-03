package com.flowiee.pms.service.sales;

import com.flowiee.pms.model.GeneralLedger;

import java.time.LocalDate;

public interface LedgerService {
    GeneralLedger findGeneralLedger(int pageSize, int pageNum, LocalDate fromDate, LocalDate toDate);
}
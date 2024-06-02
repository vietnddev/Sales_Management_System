package com.flowiee.pms.service.sales;

import com.flowiee.pms.model.GeneralLedger;

public interface LedgerService {
    GeneralLedger findGeneralLedger(int pageSize, int pageNum);
}
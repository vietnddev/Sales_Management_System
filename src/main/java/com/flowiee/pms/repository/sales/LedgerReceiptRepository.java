package com.flowiee.pms.repository.sales;

import com.flowiee.pms.entity.sales.LedgerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerReceiptRepository extends JpaRepository<LedgerReceipt, Integer> {
    @Query(value = "select receipt_index from ledger_receipt order by created_by desc fetch first 1 rows only", nativeQuery = true)
    Integer findLastIndex();
}
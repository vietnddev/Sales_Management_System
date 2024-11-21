package com.flowiee.pms.repository.sales;

import com.flowiee.pms.entity.sales.LoyaltyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyTransactionRepository extends JpaRepository<LoyaltyTransaction, Long> {
}
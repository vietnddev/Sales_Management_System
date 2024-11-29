package com.flowiee.pms.repository.sales;

import com.flowiee.pms.entity.sales.LoyaltyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyRuleRepository extends JpaRepository<LoyaltyRule, Long> {

}
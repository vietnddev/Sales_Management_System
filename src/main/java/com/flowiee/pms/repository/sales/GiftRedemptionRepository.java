package com.flowiee.pms.repository.sales;

import com.flowiee.pms.entity.sales.GiftRedemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRedemptionRepository extends JpaRepository<GiftRedemption, Long> {

}
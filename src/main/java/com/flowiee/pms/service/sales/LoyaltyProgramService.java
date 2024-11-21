package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.LoyaltyProgram;
import com.flowiee.pms.service.BaseCurdService;

import java.util.List;

public interface LoyaltyProgramService extends BaseCurdService<LoyaltyProgram> {
    LoyaltyProgram findById(Long programId, boolean throwException);

    List<LoyaltyProgram> getActivePrograms();

    LoyaltyProgram enableProgram(Long programId);

    LoyaltyProgram disableProgram(Long programId);

    void accumulatePoints(Long customerId, Long programId, int points); // Tích điểm

    void redeemPoints(Long customerId, int pointsToRedeem); // Đổi điểm
}
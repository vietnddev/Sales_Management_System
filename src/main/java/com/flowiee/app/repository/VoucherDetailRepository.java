package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.VoucherDetail;

import java.util.List;

@Repository
public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, Integer> {
    @Query("from VoucherDetail v where v.voucher.id=:voucherId")
    List<VoucherDetail> findByVoucherId(Integer voucherId);
}
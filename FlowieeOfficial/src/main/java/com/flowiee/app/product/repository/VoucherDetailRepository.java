package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.VoucherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, Integer> {
    @Query("from VoucherDetail v where v.voucher.id=:voucherId")
    List<VoucherDetail> findByVoucherId(Integer voucherId);
}
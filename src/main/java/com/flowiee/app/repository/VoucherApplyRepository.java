package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.VoucherApply;

import java.util.List;

@Repository
public interface VoucherApplyRepository extends JpaRepository<VoucherApply, Integer> {
    @Query("from VoucherApply where voucherId=:voucherId")
    List<VoucherApply> findByVoucherId(@Param("voucherId") Integer voucherId);
}
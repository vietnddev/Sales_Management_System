package com.flowiee.app.repository;

import com.flowiee.app.entity.VoucherTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherTicketlRepository extends JpaRepository<VoucherTicket, Integer> {
    @Query("from VoucherTicket v where v.voucherInfo.id=:voucherId")
    Page<VoucherTicket> findByVoucherId(@Param("voucherId") Integer voucherId, Pageable pageable);
    
    @Query("from VoucherTicket v where v.code=:code")
    VoucherTicket findByCode(@Param("code") String code);
}
package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.VoucherInfo;

@Repository
public interface VoucherInfoRepository extends JpaRepository<VoucherInfo, Integer> {
}
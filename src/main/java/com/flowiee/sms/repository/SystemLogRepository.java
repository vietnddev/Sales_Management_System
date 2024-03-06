package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.SystemLog;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Integer> {
}
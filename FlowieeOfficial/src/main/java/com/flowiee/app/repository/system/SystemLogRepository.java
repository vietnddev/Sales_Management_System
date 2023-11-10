package com.flowiee.app.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.system.SystemLog;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Integer> {

}

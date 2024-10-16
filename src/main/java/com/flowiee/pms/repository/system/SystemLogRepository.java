package com.flowiee.pms.repository.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.system.SystemLog;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    @Query("from SystemLog s order by s.createdAt desc")
    Page<SystemLog> findAll(Pageable pageable);
}
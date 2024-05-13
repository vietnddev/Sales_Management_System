package com.flowiee.pms.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.system.SystemConfig;

import java.util.List;

@Repository
public interface FlowieeConfigRepository extends JpaRepository<SystemConfig, Integer> {
    @Query("from SystemConfig order by sort")
    List<SystemConfig> findAll();

    SystemConfig findByCode(String code);

    @Query("from SystemConfig where code in :listCode")
    List<SystemConfig> findByCode(List<String> listCode);
}
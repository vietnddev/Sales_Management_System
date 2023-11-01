package com.flowiee.app.system.repository;

import com.flowiee.app.system.entity.FlowieeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowieeConfigRepository extends JpaRepository<FlowieeConfig, Integer> {
    @Query("from FlowieeConfig order by sort")
    List<FlowieeConfig> findAll();

    FlowieeConfig findByKey(String key);
}
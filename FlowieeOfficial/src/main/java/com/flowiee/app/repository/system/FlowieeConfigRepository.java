package com.flowiee.app.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.FlowieeConfig;

import java.util.List;

@Repository
public interface FlowieeConfigRepository extends JpaRepository<FlowieeConfig, Integer> {
    @Query("from FlowieeConfig order by sort")
    List<FlowieeConfig> findAll();

    FlowieeConfig findByKey(String key);
}
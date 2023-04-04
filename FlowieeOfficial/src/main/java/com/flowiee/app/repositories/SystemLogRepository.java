package com.flowiee.app.repositories;

import com.flowiee.app.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Integer> {

}

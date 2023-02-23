package com.flowiee.app.repositories;

import com.flowiee.app.model.admin.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    public List<Log> findByType(String type);

}

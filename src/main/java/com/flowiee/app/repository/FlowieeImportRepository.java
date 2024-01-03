package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.FlowieeImport;

import java.util.Date;
import java.util.List;

@Repository
public interface FlowieeImportRepository extends JpaRepository<FlowieeImport, Integer> {
    @Query("from FlowieeImport i where i.account.id=:accountId")
    List<FlowieeImport> findByAccountId(@Param("accountId") Integer accountId);

    @Query("from FlowieeImport i where i.startTime=:startTime")
    FlowieeImport findByStartTime(@Param("startTime") Date startTime);
}
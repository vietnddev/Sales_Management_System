package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.FlowieeImport;

import java.util.Date;
import java.util.List;

@Repository
public interface AppImportRepository extends JpaRepository<FlowieeImport, Integer> {
    @Query("from FlowieeImport i where i.account.id=:accountId")
    List<FlowieeImport> findByAccountId(@Param("accountId") Integer accountId);

    @Query("from FlowieeImport i where i.startTime=:startTime")
    FlowieeImport findByStartTime(@Param("startTime") Date startTime);
}
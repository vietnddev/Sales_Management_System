package com.flowiee.pms.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.system.FileImportHistory;

import java.util.Date;
import java.util.List;

@Repository
public interface AppImportRepository extends JpaRepository<FileImportHistory, Integer> {
    @Query("from FileImportHistory i where i.account.id=:accountId")
    List<FileImportHistory> findByAccountId(@Param("accountId") Integer accountId);

    @Query("from FileImportHistory i where i.beginTime=:startTime")
    FileImportHistory findByStartTime(@Param("startTime") Date startTime);
}
package com.flowiee.app.hethong.repository;

import com.flowiee.app.hethong.entity.FlowieeImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowieeImportRepository extends JpaRepository<FlowieeImport, Integer> {
    @Query("from FlowieeImport i where i.account.id=:accountId")
    List<FlowieeImport> findByAccountId(Integer accountId);
}
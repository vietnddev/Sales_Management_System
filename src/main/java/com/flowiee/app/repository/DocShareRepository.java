package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.DocShare;

@Repository
public interface DocShareRepository extends JpaRepository<DocShare, Integer> {
    @Query("from DocShare d where d.document=:documentId and d.account=:accountId")
    DocShare findByDocmentAndTaiKhoan(@Param("documentId") Integer documentId, @Param("accountId") Integer accountId);
}
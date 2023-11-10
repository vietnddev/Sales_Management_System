package com.flowiee.app.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.DocShare;

@Repository
public interface DocShareRepository extends JpaRepository<DocShare, Integer> {
    @Query("from DocShare d where d.document=:documentId and d.account=:accountId")
    DocShare findByDocmentAndTaiKhoan(int documentId, int accountId);
}
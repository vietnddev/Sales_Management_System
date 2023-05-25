package com.flowiee.app.khotailieu.repository;

import com.flowiee.app.khotailieu.entity.DocShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocShareRepository extends JpaRepository<DocShare, Integer> {
    @Query("from DocShare d where d.document=:documentId and d.taiKhoan=:taiKhoanId")
    DocShare findByDocmentAndTaiKhoan(int documentId, int taiKhoanId);
}
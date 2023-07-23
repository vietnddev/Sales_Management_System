package com.flowiee.app.khotailieu.repository;

import com.flowiee.app.khotailieu.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query("from Document d where d.parentId = 0 order by d.loai desc")
    List<Document> findRootDocument();

    @Query("from Document d where d.parentId =:parentId order by d.loai desc")
    List<Document> findListDocument(int parentId);

    @Query("from Document d where d.loai=:isThuMuc")
    List<Document> findListFolder(String isThuMuc);
}
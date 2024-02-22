package com.flowiee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowiee.app.entity.Document;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query("from Document d where d.parentId=:parentId")
    Page<Document> findAll(@Param("parentId") Integer parentId, Pageable pageable);

    @Query("from Document d where d.parentId =:parentId order by d.isFolder desc")
    List<Document> findListDocumentByParentId(@Param("parentId") Integer parentId);

    @Query("from Document d where d.parentId =:parentId and d.isFolder = 'FOLDER' order by d.isFolder desc")
    List<Document> findListFolderByParentId(@Param("parentId") Integer parentId);

    @Query("from Document d where d.parentId =:parentId and d.isFolder = 'FILE' order by d.isFolder desc")
    List<Document> findListFileByParentId(@Param("parentId") Integer parentId);

    @Query("from Document d where d.isFolder=:isThuMuc")
    List<Document> findAllFolder(@Param("isThuMuc") String isThuMuc);

    @Query("from Document d where d.loaiTaiLieu.id=:docTypeId")
    List<Document> findDocumentByDocTypeId(@Param("docTypeId") Integer docTypeId);
}
package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowiee.app.entity.Document;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query("from Document d where d.parentId = 0 order by d.loai desc")
    List<Document> findRootDocument();

    @Query("from Document d where d.parentId = 0 and d.loai = 'FOLDER' order by d.loai desc")
    List<Document> findRootFolder();

    @Query("from Document d where d.parentId = 0 and d.loai = 'FILE' order by d.loai desc")
    List<Document> findRootFile();

    @Query("from Document d where d.parentId =:parentId order by d.loai desc")
    List<Document> findListDocumentByParentId(@Param("parentId") Integer parentId);

    @Query("from Document d where d.parentId =:parentId and d.loai = 'FOLDER' order by d.loai desc")
    List<Document> findListFolderByParentId(@Param("parentId") Integer parentId);

    @Query("from Document d where d.parentId =:parentId and d.loai = 'FILE' order by d.loai desc")
    List<Document> findListFileByParentId(@Param("parentId") Integer parentId);

    @Query("from Document d where d.loai=:isThuMuc")
    List<Document> findAllFolder(@Param("isThuMuc") String isThuMuc);

    @Query("from Document d where d.loaiTaiLieu.id=:docTypeId")
    List<Document> findDocumentByDocTypeId(@Param("docTypeId") Integer docTypeId);
}
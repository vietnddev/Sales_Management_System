package com.flowiee.app.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowiee.app.entity.storage.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query("from Document d where d.parentId = 0 order by d.loai desc")
    List<Document> findRootDocument();

    @Query("from Document d where d.parentId = 0 and d.loai = 'FOLDER' order by d.loai desc")
    List<Document> findRootFolder();

    @Query("from Document d where d.parentId = 0 and d.loai = 'FILE' order by d.loai desc")
    List<Document> findRootFile();

    @Query("from Document d where d.parentId =:parentId order by d.loai desc")
    List<Document> findListDocumentByParentId(int parentId);

    @Query("from Document d where d.parentId =:parentId and d.loai = 'FOLDER' order by d.loai desc")
    List<Document> findListFolderByParentId(int parentId);

    @Query("from Document d where d.parentId =:parentId and d.loai = 'FILE' order by d.loai desc")
    List<Document> findListFileByParentId(int parentId);

    @Query("from Document d where d.loai=:isThuMuc")
    List<Document> findAllFolder(String isThuMuc);

    @Query("from Document d where d.loaiTaiLieu.id=:docTypeId")
    List<Document> findDocumentByDocTypeId(Integer docTypeId);
}
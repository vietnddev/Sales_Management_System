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

//    @Query(value = "WITH DocumentHierarchy(ID, NAME, AS_NAME, PARENT_ID, H_LEVEL) AS ( " +
//                   "    SELECT ID, NAME, AS_NAME, PARENT_ID, 1 " +
//                   "    FROM STG_DOCUMENT " +
//                   "    WHERE id =:documentId " +
//                   "    UNION ALL " +
//                   "    SELECT d.ID, d.NAME, d.AS_NAME ,d.PARENT_ID, dh.H_LEVEL + 1 " +
//                   "    FROM STG_DOCUMENT d " +
//                   "    INNER JOIN DocumentHierarchy dh ON dh.PARENT_ID = d.id " +
//                   "), " +
//                   "DocumentToFindParent(ID, NAME, AS_NAME, PARENT_ID, H_LEVEL) AS ( " +
//                   "    SELECT ID, NAME, AS_NAME, PARENT_ID, NULL AS H_LEVEL " +
//                   "    FROM STG_DOCUMENT " +
//                   "    WHERE ID =:documentId " +
//                   ") " +
//                   "SELECT ID, NAME, CONCAT(CONCAT(AS_NAME, '-'), ID) AS AS_NAME, PARENT_ID, H_LEVEL " +
//                   "FROM DocumentHierarchy " +
//                   "UNION ALL " +
//                   "SELECT ID, NAME, CONCAT(CONCAT(AS_NAME, '-'), ID) AS AS_NAME, PARENT_ID, H_LEVEL " +
//                   "FROM DocumentToFindParent " +
//                   "START WITH ID =:parentId " +
//                   "CONNECT BY PRIOR PARENT_ID = ID " +
//                   "ORDER BY H_LEVEL DESC",
//            nativeQuery = true)
//    List<Object[]> findBreadcrumbItems(@Param("documentId") Integer documentId, @Param("parentId") Integer parentId);
}
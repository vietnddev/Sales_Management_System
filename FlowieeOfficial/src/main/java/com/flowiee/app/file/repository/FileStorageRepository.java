package com.flowiee.app.file.repository;

import com.flowiee.app.file.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface FileStorageRepository extends JpaRepository<FileStorage, Integer> {
    @Query("from FileStorage f where f.module=:module")
    List<FileStorage> findAllImageSanPham(String module);

    @Query("from FileStorage f where f.sanPham.id=:sanPhamId and f.bienTheSanPham.id is null order by f.createdAt")
    List<FileStorage> findImageOfSanPham(int sanPhamId);

    @Query("from FileStorage f where f.bienTheSanPham.id=:bienTheSanPhamId order by f.createdAt")
    List<FileStorage> findImageOfSanPhamBienThe(int bienTheSanPhamId);

    @Query("from FileStorage f where f.document.id=:documentId order by f.createdAt desc")
    List<FileStorage> findFileOfDocument(int documentId);

    @Query("from FileStorage f where f.document.id=:documentId and f.isActive=:isActive")
    FileStorage findFileIsActiveOfDocument(int documentId, boolean isActive);

    @Query("from FileStorage f where f.sanPham.id=:sanPhamId and f.bienTheSanPham.id is null and f.isActive=:isActive")
    FileStorage findImageActiveOfSanPham(int sanPhamId, boolean isActive);

    @Query("from FileStorage f where f.bienTheSanPham.id=:bienTheSanPhamId and f.isActive=:isActive")
    FileStorage findImageActiveOfSanPhamBienThe(int bienTheSanPhamId, boolean isActive);

    @Query("from FileStorage f where f.createdAt=:createdTime")
    FileStorage findByCreatedTime(Date createdTime);
}
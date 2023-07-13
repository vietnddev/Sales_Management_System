package com.flowiee.app.file.repository;

import com.flowiee.app.file.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Integer> {
    @Query("from FileStorage f where f.module=:module")
    List<FileStorage> findAllImageSanPham(String module);

    @Query("from FileStorage f where f.sanPham.id=:idSanPham")
    List<FileStorage> findImageOfSanPham(int idSanPham);

    @Query("from FileStorage f where f.bienTheSanPham.id=:bienTheSanPhamId")
    List<FileStorage> findImageOfSanPhamBienThe(int bienTheSanPhamId);

    @Query("from FileStorage f where f.document.id=:documentId order by f.isActive desc")
    List<FileStorage> findFileOfDocument(int documentId);
}
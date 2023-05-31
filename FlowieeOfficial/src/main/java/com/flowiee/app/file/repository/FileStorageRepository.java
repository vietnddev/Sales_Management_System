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
} 
               
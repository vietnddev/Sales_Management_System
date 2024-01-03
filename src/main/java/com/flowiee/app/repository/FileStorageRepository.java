package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flowiee.app.entity.FileStorage;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface FileStorageRepository extends JpaRepository<FileStorage, Integer> {
    @Query("from FileStorage f where f.module=:module")
    List<FileStorage> findAllImageSanPham(@Param("module") String module);

    @Query("from FileStorage f where f.product.id=:productId and f.productVariant.id is null order by f.createdAt")
    List<FileStorage> findImageOfSanPham(@Param("productId") Integer productId);

    @Query("from FileStorage f where f.productVariant.id=:productVariantId order by f.createdAt")
    List<FileStorage> findImageOfSanPhamBienThe(@Param("productVariantId") Integer productVariantId);

    @Query("from FileStorage f where f.document.id=:documentId order by f.createdAt desc")
    List<FileStorage> findFileOfDocument(@Param("documentId") Integer documentId);

    @Query("from FileStorage f where f.document.id=:documentId and f.isActive=:isActive")
    FileStorage findFileIsActiveOfDocument(@Param("documentId") Integer documentId, @Param("isActive") boolean isActive);

    @Query("from FileStorage f where f.product.id=:productId and f.productVariant.id is null and f.isActive=:isActive")
    FileStorage findImageActiveOfSanPham(@Param("productId") Integer productId, @Param("isActive") boolean isActive);

    @Query("from FileStorage f where f.productVariant.id=:productVariantId and f.isActive=:isActive")
    FileStorage findImageActiveOfSanPhamBienThe(@Param("productVariantId") Integer productVariantId, @Param("isActive") boolean isActive);

    @Query("from FileStorage f where f.createdAt=:createdTime")
    FileStorage findByCreatedTime(@Param("createdTime") Date createdTime);

    @Query("from FileStorage f where f.order.id=:orderId")
    FileStorage findQRCodeOfOrder(@Param("orderId") Integer orderId);
}
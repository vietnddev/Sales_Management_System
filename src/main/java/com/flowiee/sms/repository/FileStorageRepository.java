package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flowiee.sms.entity.FileStorage;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface FileStorageRepository extends JpaRepository<FileStorage, Integer> {
    @Query("from FileStorage f where f.module=:module")
    List<FileStorage> findAllImageProduct(@Param("module") String module);

    @Query("from FileStorage f where f.product.id=:productId and f.productDetail.id is null order by f.createdAt")
    List<FileStorage> findImageOfProduct(@Param("productId") Integer productId);

    @Query("from FileStorage f where f.productDetail.id=:productVariantId order by f.createdAt")
    List<FileStorage> findImageOfProductDetail(@Param("productVariantId") Integer productVariantId);

    @Query("from FileStorage f where f.product.id=:productId and f.productDetail.id is null and f.isActive=:isActive")
    FileStorage findImageActiveOfProduct(@Param("productId") Integer productId, @Param("isActive") boolean isActive);

    @Query("from FileStorage f where f.productDetail.id=:productVariantId and f.isActive=:isActive")
    FileStorage findImageActiveOfProductDetail(@Param("productVariantId") Integer productVariantId, @Param("isActive") boolean isActive);

    @Query("from FileStorage f where f.createdAt=:createdTime")
    FileStorage findByCreatedTime(@Param("createdTime") Date createdTime);

    @Query("from FileStorage f where f.order.id=:orderId")
    FileStorage findQRCodeOfOrder(@Param("orderId") Integer orderId);
}
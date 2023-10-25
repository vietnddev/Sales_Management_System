package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.GoodsImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsImportRepository extends JpaRepository<GoodsImport, Integer> {
//    @Query("from GoodsImport i where i.material.id=:materialId")
//    List<GoodsImport> findByMaterialId(Integer materialId);

    @Query("from GoodsImport i where i.supplier.id=:supplierId")
    List<GoodsImport> findBySupplierId(Integer supplierId);

    @Query("from GoodsImport i where i.paymentMethod=:paymentMethod")
    List<GoodsImport> findByPaymentMethod(String paymentMethod);

    @Query("from GoodsImport i where i.paidStatus=:paidStatus")
    List<GoodsImport> findByPaidStatus(String paidStatus);

    @Query("from GoodsImport i where i.receivedBy.id=:accountId")
    List<GoodsImport> findByReceiveBy(Integer accountId);

    @Query("from GoodsImport i where i.status=:status and i.createdBy.id=:createdBy")
    GoodsImport findDraftGoodsImportPresent(String status, String createdBy);
}
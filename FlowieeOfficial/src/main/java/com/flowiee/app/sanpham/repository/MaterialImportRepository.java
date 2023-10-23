package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.MaterialImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialImportRepository extends JpaRepository<MaterialImport, Integer> {
    @Query("from MaterialImport m where m.material.id=:materialId")
    List<MaterialImport> findByMaterialId(Integer materialId);

    @Query("from MaterialImport m where m.supplier.id=:supplierId")
    List<MaterialImport> findBySupplierId(Integer supplierId);

    @Query("from MaterialImport m where m.paymentMethod=:paymentMethod")
    List<MaterialImport> findByPaymentMethod(String paymentMethod);

    @Query("from MaterialImport m where m.paidStatus=:paidStatus")
    List<MaterialImport> findByPaidStatus(String paidStatus);

    @Query("from MaterialImport m where m.receivedBy.id=:accountId")
    List<MaterialImport> findByAccountId(Integer accountId);
}
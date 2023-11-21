package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.TicketImportGoods;

import java.util.List;

@Repository
public interface GoodsImportRepository extends JpaRepository<TicketImportGoods, Integer> {
//    @Query("from GoodsImport i where i.material.id=:materialId")
//    List<GoodsImport> findByMaterialId(Integer materialId);

    @Query("from TicketImportGoods i where i.supplier.id=:supplierId")
    List<TicketImportGoods> findBySupplierId(Integer supplierId);

    @Query("from TicketImportGoods i where i.paymentMethod=:paymentMethod")
    List<TicketImportGoods> findByPaymentMethod(String paymentMethod);

    @Query("from TicketImportGoods i where i.paidStatus=:paidStatus")
    List<TicketImportGoods> findByPaidStatus(String paidStatus);

    @Query("from TicketImportGoods i where i.receivedBy.id=:accountId")
    List<TicketImportGoods> findByReceiveBy(Integer accountId);

    @Query("from TicketImportGoods i where i.status=:status and i.createdBy=:createdBy")
    TicketImportGoods findDraftGoodsImportPresent(String status, Integer createdBy);
}
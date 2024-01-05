package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.TicketImportGoods;

import java.util.List;

@Repository
public interface TicketImportRepository extends JpaRepository<TicketImportGoods, Integer> {
    @Query("from TicketImportGoods i " +
           "where (:text is null or i.title like %:text%) " +
           "and (:supplierId is null or i.supplier.id=:supplierId) " +
           "and (:paymentMethod is null or i.paymentMethod.id=:paymentMethod) " +
           "and (:payStatus is null or i.paidStatus=:payStatus) " +
           "and (:importStatus is null or i.status=:importStatus) ")
    List<TicketImportGoods> findAll(@Param("text") String text, @Param("supplierId") Integer supplierId,
                                    @Param("paymentMethod") Integer paymentMethod, @Param("payStatus") String payStatus,
                                    @Param("importStatus") String importStatus);

//    @Query("from GoodsImport i where i.material.id=:materialId")
//    List<GoodsImport> findByMaterialId(Integer materialId);

    @Query("from TicketImportGoods i where i.supplier.id=:supplierId")
    List<TicketImportGoods> findBySupplierId(@Param("supplierId") Integer supplierId);

    @Query("from TicketImportGoods i where i.paymentMethod=:paymentMethod")
    List<TicketImportGoods> findByPaymentMethod(@Param("paymentMethod") String paymentMethod);

    @Query("from TicketImportGoods i where i.paidStatus=:paidStatus")
    List<TicketImportGoods> findByPaidStatus(@Param("paidStatus") String paidStatus);

    @Query("from TicketImportGoods i where i.receivedBy.id=:accountId")
    List<TicketImportGoods> findByReceiveBy(@Param("accountId") Integer accountId);

    @Query("from TicketImportGoods i where i.status=:status and i.createdBy=:createdBy")
    TicketImportGoods findDraftGoodsImportPresent(@Param("status") String status, @Param("createdBy") Integer createdBy);
}
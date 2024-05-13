package com.flowiee.pms.repository.sales;

import com.flowiee.pms.entity.sales.TicketExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketExportRepository extends JpaRepository<TicketExport, Integer> {
    @Query("from TicketExport t " +
           "where 1=1 " +
           "and (:storageId is null or t.storage.id=:storageId)")
    Page<TicketExport> findAll(@Param("storageId") Integer storageId, Pageable pageable);

//    @Modifying
//    @Query("update TicketExport set title=:title, note=:note, status=:status where id=:ticketExportId")
//    TicketExport update(@Param("title") String title, @Param("note") String note, @Param("status") String status, @Param("ticketExportId") Integer ticketExportId);
}
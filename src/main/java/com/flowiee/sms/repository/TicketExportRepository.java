package com.flowiee.sms.repository;

import com.flowiee.sms.entity.TicketExport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketExportRepository extends JpaRepository<TicketExport, Integer> {
//    @Modifying
//    @Query("update TicketExport set title=:title, note=:note, status=:status where id=:ticketExportId")
//    TicketExport update(@Param("title") String title, @Param("note") String note, @Param("status") String status, @Param("ticketExportId") Integer ticketExportId);
}
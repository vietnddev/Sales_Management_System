package com.flowiee.app.repository;

import com.flowiee.app.entity.TicketExport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketExportRepository extends JpaRepository<TicketExport, Integer> {
//    @Modifying
//    @Query("update TicketExport set title=:title, note=:note, status=:status where id=:ticketExportId")
//    TicketExport update(@Param("title") String title, @Param("note") String note, @Param("status") String status, @Param("ticketExportId") Integer ticketExportId);
}
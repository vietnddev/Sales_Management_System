package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;
import com.flowiee.pms.model.dto.TicketExportDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ticket_export_goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketExport extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "title")
    private String title;

    @Column(name = "exporter", nullable = false)
    private String exporter;

    @Column(name = "export_time", nullable = false)
    private Date exportTime;

    @Column(name = "note", length = 500)
    private String note;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketExport", fetch = FetchType.LAZY)
    private List<Order> listOrders;

    public static TicketExport fromTicketExportDTO(TicketExportDTO dto) {
        TicketExport ticketExport = new TicketExport();
        ticketExport.setId(dto.getId());
        ticketExport.setTitle(dto.getTitle());
        ticketExport.setExporter(dto.getExporter());
        ticketExport.setExportTime(dto.getExportTime());
        ticketExport.setNote(dto.getNote());
        ticketExport.setStatus(dto.getStatus());
        return ticketExport;
    }
}
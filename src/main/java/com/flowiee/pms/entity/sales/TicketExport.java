package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.model.dto.TicketExportDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @Column(name = "title")
    private String title;

    @Column(name = "exporter", nullable = false)
    private String exporter;

    @Column(name = "export_time", nullable = false)
    private LocalDateTime exportTime;

    @Column(name = "note", length = 500)
    private String note;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketExport", fetch = FetchType.LAZY)
    private List<Order> listOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketExport", fetch = FetchType.LAZY)
    private List<ProductVariantTemp> listProductVariantTemp;

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
package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stg_ticket_export_goods")
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
}
package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.entity.system.FileStorage;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Table(name = "ticket_export_goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketExport extends BaseEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    Storage storage;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "exporter", nullable = false)
    String exporter;

    @Column(name = "export_time", nullable = false)
    LocalDateTime exportTime;

    @Column(name = "note", length = 500)
    String note;

    @Column(name = "status")
    String status;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketExport", fetch = FetchType.LAZY)
    List<Order> listOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketExport", fetch = FetchType.LAZY)
    List<ProductVariantTemp> listProductVariantTemp;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketExport", fetch = FetchType.LAZY)
    List<FileStorage> listImages;

    @Transient
    Integer totalItems;

    @Transient
    BigDecimal totalValue;

    @Transient
    String storageName;
}
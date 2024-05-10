package com.flowiee.pms.entity.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;
import com.flowiee.pms.entity.sales.TicketImport;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "storage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Storage extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "location")
    private String location;

    @Column(name = "area")
    private Double area;

    @Column(name = "holdable_quantity")
    private Integer holdableQty;

    @Column(name = "hold_warning_percent")
    private Integer holdWarningPercent;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "storage", fetch = FetchType.LAZY)
    private List<TicketImport> listTicketImports;
}
package com.flowiee.pms.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;

import com.flowiee.pms.entity.sales.TicketImport;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Builder
@Entity
@Table(name = "product_detail_temp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariantTemp extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_import_id", nullable = false)
    private TicketImport ticketImport;

	@Column(name = "product_variant_id", nullable = false)
	private Integer productVariantId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "note")
    private String note;
}
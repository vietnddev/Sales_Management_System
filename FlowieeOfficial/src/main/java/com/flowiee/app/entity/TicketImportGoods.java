package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.category.Category;

import lombok.*;

import javax.persistence.*;
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
public class TicketImportGoods extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "discount")
    private Float discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method")
    private Category paymentMethod;

    @Column(name = "paid_amount")
    private Float paidAmount;

    @Column(name = "pay_status")
    private String paidStatus;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "received_time")
    private Date receivedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private Account receivedBy;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "goodsImport", fetch = FetchType.LAZY)
    private List<Material> listMaterial;

    @OneToMany(mappedBy = "goodsImport", fetch = FetchType.LAZY)
    private List<ProductVariant> listProductVariant;

    @OneToMany(mappedBy = "goodsImport", fetch = FetchType.LAZY)
    private List<MaterialTemp> listMaterialTemp;

    @OneToMany(mappedBy = "goodsImport", fetch = FetchType.LAZY)
    private List<ProductVariantTemp> listProductVariantTemp;

    public TicketImportGoods(Integer id) {
        super.id = id;
    }
}
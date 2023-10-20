package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "material_import")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MaterialImport extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "discount")
    private Float discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method", nullable = false)
    private HinhThucThanhToan paymentMethod;

    @Column(name = "paidAmount")
    private Float paidAmount;

    @Column(name = "pay_status", nullable = false)
    private String payStatus;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "received_time")
    private Date receivedTime;

    @Column(name = "note")
    private String note;
}
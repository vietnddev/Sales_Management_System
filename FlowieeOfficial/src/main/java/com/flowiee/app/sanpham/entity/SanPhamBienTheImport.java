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
@Table(name = "product_variant_import")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SanPhamBienTheImport extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private BienTheSanPham bienTheSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garment_factory_id", nullable = false)
    private GarmentFactory garmentFactory;

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

    @Column(name = "done_time")
    private Date doneTime;

    @Column(name = "received_time")
    private Date receivedTime;

    @Column(name = "note")
    private String note;
}
package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.storage.entity.Material;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "goods_import")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GoodsImport extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "discount")
    private Float discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method")
    private HinhThucThanhToan paymentMethod;

    @Column(name = "paidAmount")
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
    private List<BienTheSanPham> listBienTheSanPham;
}
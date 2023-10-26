package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Entity
@Table(name = "voucher_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherDetail extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher", nullable = false)
    private Voucher voucher;

    @Column(name = "code", nullable = false, length = 15)
    private String key;

    @Column(name = "active_time")
    private Date activeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang")
    private KhachHang khachHang;

    @Column(name = "status", nullable = false)
    private boolean status;
}
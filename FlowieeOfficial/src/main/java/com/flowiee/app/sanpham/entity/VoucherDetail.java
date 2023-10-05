package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import com.flowiee.app.danhmuc.entity.DonViTinh;
import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.file.entity.FileStorage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Column(name = "key_voucher", nullable = false, length = 15)
    private String key;

    @Column(name = "active_time")
    private Date activeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang")
    private KhachHang khachHang;

    @Column(name = "status", nullable = false)
    private boolean status;
}
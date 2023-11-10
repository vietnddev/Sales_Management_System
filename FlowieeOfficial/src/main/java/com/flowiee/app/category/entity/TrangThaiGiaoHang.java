package com.flowiee.app.category.entity;

import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.product.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trang_thai_giao_hang")
public class TrangThaiGiaoHang extends BaseEntity implements Serializable {
    @Column(name = "thoi_gian")
    private Date thoiGian;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id", nullable = false)
    private Order order;
}
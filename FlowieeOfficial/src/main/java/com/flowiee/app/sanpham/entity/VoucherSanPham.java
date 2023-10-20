package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Table(name = "voucher_apply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherSanPham extends BaseEntity implements Serializable {
    @Column(name = "san_pham_id", nullable = false)
    private Integer sanPhamId;

    @Column(name = "voucher_id", nullable = false)
    private Integer voucherId;
}
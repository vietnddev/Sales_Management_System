package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Entity
@Table(name = "voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Voucher extends BaseEntity implements Serializable {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "doi_tuong_ap_dung", nullable = false)
    private String doiTuongApDung;

    @Column(name = "type", length = 1000)
    private String voucherType;

    @Column(name = "so_luong", nullable = false)
    private Integer quantity;

    @Column(name = "length_of_key", nullable = false)
    private Integer lengthOfKey;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "max_price_discount")
    private Float maxPriceDiscount;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Column(name = "status", nullable = false)
    private boolean status;
}
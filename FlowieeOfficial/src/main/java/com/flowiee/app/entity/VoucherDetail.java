package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Entity
@Table(name = "voucher_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherDetail extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher", nullable = false)
    private Voucher voucher;

    @Column(name = "code", nullable = false, length = 15)
    private String key;

    @Column(name = "active_time")
    private Date activeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang")
    private Customer customer;

    @Column(name = "status", nullable = false)
    private boolean status;
}
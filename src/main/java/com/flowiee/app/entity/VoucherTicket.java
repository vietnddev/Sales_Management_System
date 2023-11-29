package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Entity
@Table(name = "pro_voucher_ticket")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherTicket extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_info_id", nullable = false)
    private VoucherInfo voucherInfo;

    @Column(name = "code", nullable = false, length = 15)
    private String code;

    @Column(name = "active_time")
    private Date activeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang")
    private Customer customer;

    @Column(name = "status", nullable = false)
    private boolean status;
}
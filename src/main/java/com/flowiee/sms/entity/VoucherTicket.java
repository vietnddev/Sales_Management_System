package com.flowiee.sms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.sms.core.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
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
    @Serial
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_info_id", nullable = false)
    private VoucherInfo voucherInfo;

    @Column(name = "code", nullable = false, length = 15)
    private String code;

    @Column(name = "length", nullable = false)
    private Integer length;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "active_time")
    private Date activeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column(name = "status", nullable = false)
    private boolean status;

	@Override
	public String toString() {
		return "VoucherTicket [id=" + super.id + ", voucherInfo=" + voucherInfo + ", code=" + code + ", activeTime=" + activeTime
				+ ", customer=" + customer + ", status=" + status + "]";
	}
}
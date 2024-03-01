package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Builder
@Entity
@Table(name = "pro_voucher_apply")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherApply extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "voucher_id", nullable = false)
    private Integer voucherId;

	@Override
	public String toString() {
		return "VoucherApply [id=" + super.id + ", productId=" + productId + ", voucherId=" + voucherId + "]";
	}
}